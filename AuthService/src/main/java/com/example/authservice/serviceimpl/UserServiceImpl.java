package com.example.authservice.serviceimpl;

import com.example.authservice.dto.*;
import com.example.authservice.entity.RefreshToken;
import com.example.authservice.entity.User;
import com.example.authservice.enums.RoleType;
import com.example.authservice.exception.EmailAlreadyExistsException;
import com.example.authservice.exception.InvalidCredentialsException;
import com.example.authservice.exception.PhoneNumberAlreadyExistsException;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.repository.RefreshTokenRepository;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtUtil;
import com.example.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private EmailProducer emailProducer;


    public RegistrationResponseDTO register(RegistrationRequestDTO requestDTO) {

        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        if (userRepository.findByPhoneNumber(requestDTO.getPhoneNumber()).isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Phone number already in use");
        }

        User user = UserMapper.mapToEntity(requestDTO);
        user.setRoleType(RoleType.USER);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User savedUser = userRepository.save(user);

        emailProducer.sendEmailRequest(
                new EmailRequestDTO(
                        user.getEmail(),
                        "Welcome to Auth-Service!",
                        "Hi " + user.getFullName() + ",\n\nThank you for registering with Auth-Service. We're excited to have you on board!"
                )
        );

        return UserMapper.mapToDTO(savedUser);
    }

    public LoginResponseDTO login(String email, String password,String deviceInfo) {

          User user = userRepository.findByEmail(email)
                  .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new InvalidCredentialsException("Incorrect password");
            }

            String accessToken = jwtUtil.generateToken(email, user.getRoleType().name());
            String refreshTokenString = jwtUtil.generateRefreshToken(email);
            Date expiryDate = jwtUtil.getExpiration(refreshTokenString);

            RefreshToken refreshToken = RefreshToken.builder()
                    .token(refreshTokenString)
                    .expiryDate(expiryDate)
                    .user(user)
                    .revoked(false)
                    .reused(false)
                    .build();
            refreshTokenRepository.save(refreshToken);

            return LoginResponseDTO.builder()
                    .token(accessToken)
                    .refreshToken(refreshTokenString)
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .roleType(user.getRoleType())
                    .expiration(jwtUtil.getExpiration(accessToken).getTime())
                    .build();
        }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        return UserMapper.mapToUserResponseDTO(user);
    }

    public Map<String, String> refreshToken(String token) {
        if (!jwtUtil.isTokenValid(token)) {
            throw new InvalidCredentialsException("Invalid token");
        }

        RefreshToken storedToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException("Refresh token not found"));

        if (storedToken.isRevoked() || storedToken.getExpiryDate().before(new Date())) {
            storedToken.setReused(true);
            refreshTokenRepository.save(storedToken);

            List<RefreshToken> tokens = refreshTokenRepository.findByUser(storedToken.getUser());
            tokens.forEach(t -> { t.setRevoked(true); });
            refreshTokenRepository.saveAll(tokens);



            throw new SecurityException("Refresh token reuse detected — all sessions revoked");
        }

        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        String newAccessToken = jwtUtil.generateToken(storedToken.getUser().getEmail(), storedToken.getUser().getRoleType().name());
        String newRefreshToken = jwtUtil.generateRefreshToken(storedToken.getUser().getEmail());

        RefreshToken rotated = RefreshToken.builder()
                .token(newRefreshToken)
                .expiryDate(jwtUtil.getExpiration(newRefreshToken))
                .user(storedToken.getUser())
                .revoked(false)
                .reused(false)
                .build();

        refreshTokenRepository.save(rotated);

        return Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        );
    }

    public void logout(String authHeader,String refreshToken){
        if (authHeader  == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidCredentialsException("Missing or malformed token");
        }

        String token = authHeader.substring(7);

        if(!jwtUtil.isTokenValid(token)){
             throw new InvalidCredentialsException("Invalid or expired token");
        }

        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        if (refreshToken != null && !refreshToken.isEmpty()) {
            refreshTokenRepository.findByToken(refreshToken).ifPresent(rt -> {
                rt.setRevoked(true);
                refreshTokenRepository.save(rt);
            });
        }

        userRepository.save(user);
    }

    public void changePassword(String authHeader, ChangePasswordRequestDTO requestDTO) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
             throw new InvalidCredentialsException("Missing malformed token");
        }

        String token = authHeader.substring(7);
        if(!jwtUtil.isTokenValid(token)){
             throw new InvalidCredentialsException("Invalid or expired token");
        }

        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        if(!passwordEncoder.matches(requestDTO.getCurrentPassword(), user.getPassword())){
             throw new InvalidCredentialsException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));

        userRepository.save(user);

    }

    public Map<String, String> forgotPassword(ForgotPasswordRequestDTO requestDTO) {

        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        String resetToken = UUID.randomUUID().toString();
        Date expiry = new Date(System.currentTimeMillis() + 15 * 60 * 1000);
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(expiry);
        userRepository.save(user);

        return Map.of(
                "message", "Password reset token generated",
                "resetToken", resetToken
        );
    }


    public void resetPassword(ResetPasswordRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        if (user.getResetToken() == null || !user.getResetToken().equals(requestDTO.getToken())) {
            throw new InvalidCredentialsException("Invalid or expired reset token");
        }

        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().before(new Date())) {
            throw new InvalidCredentialsException("Reset token has expired");
        }

        user.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        List<RefreshToken> tokens = refreshTokenRepository.findByUser(user);
        tokens.forEach(t -> t.setRevoked(true));
        refreshTokenRepository.saveAll(tokens);

        userRepository.save(user);
    }
}