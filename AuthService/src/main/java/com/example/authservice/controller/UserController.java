    package com.example.authservice.controller;

    import com.example.authservice.dto.*;
    import com.example.authservice.service.UserService;
    import jakarta.servlet.http.Cookie;
    import jakarta.servlet.http.HttpServletResponse;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;
    import java.util.Map;

    @RestController
    @Validated
    @RequestMapping("/auth/users")
    public class UserController {

        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDTO requestDTO){

            RegistrationResponseDTO responseDTO = userService.register(requestDTO);

            return new ResponseEntity<>(Map.of(
                    "message","Registration Successfully Completed",
                    "data",responseDTO), HttpStatus.CREATED);
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO requestDTO,
                                       HttpServletResponse response) {
            LoginResponseDTO responseDTO = userService.login(
                    requestDTO.getEmail(), requestDTO.getPassword(),requestDTO.getDeviceInfo());

            Cookie cookie = new Cookie("refreshToken", responseDTO.getRefreshToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/auth/users/refresh-token");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            return ResponseEntity.ok(Map.of(
                    "message", "Login Successful",
                    "data", Map.of(
                            "token", responseDTO.getToken(),
                            "email", responseDTO.getEmail(),
                            "fullName", responseDTO.getFullName(),
                            "roleType", responseDTO.getRoleType(),
                            "expiration", responseDTO.getExpiration()
                    )
            ));
        }

        @PostMapping("/refresh-token")
        public ResponseEntity<?> refreshToken(@CookieValue("refreshToken") String refreshToken,
                                              HttpServletResponse response){
            Map<String, String> tokens = userService.refreshToken(refreshToken);

            Cookie cookie = new Cookie("refreshToken", tokens.get("refreshToken"));
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/auth/users/refresh-token");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            return ResponseEntity.ok(Map.of(
                    "message", "Token Refreshed",
                    "accessToken", tokens.get("accessToken")
            ));
        }

        @PostMapping("/logout")
        public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader,
                                        @CookieValue(value = "refreshToken", required = false) String refreshToken,
                                        HttpServletResponse response) {
            userService.logout(authHeader, refreshToken);

            Cookie deleteCookie = new Cookie("refreshToken", null);
            deleteCookie.setHttpOnly(true);
            deleteCookie.setPath("/auth/users/refresh-token");
            deleteCookie.setMaxAge(0);
            response.addCookie(deleteCookie);

            return ResponseEntity.ok(Map.of(
                    "message", "Logged out successfully"
            ));
        }

        @PostMapping("/change-password")
        public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequestDTO requestDTO,
                                                @RequestHeader("Authorization") String authHeader){
            userService.changePassword(authHeader,requestDTO);
            return new ResponseEntity<>(Map.of(
                    "message","Password change successfully"
            ),HttpStatus.OK);
        }

        @PostMapping("/forgot-password")
        public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO requestDTO) {
            Map<String, String> response = userService.forgotPassword(requestDTO);
            return ResponseEntity.ok(response);
        }

        @PostMapping("/reset-password")
        public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO requestDTO) {
            userService.resetPassword(requestDTO);
            return ResponseEntity.ok(Map.of("message", "Password reset successful"));
        }

        @GetMapping("/id/{id}")
        public ResponseEntity<?> getUserById(@PathVariable Long id) {
            UserResponseDTO responseDTO = userService.getUserById(id);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }

    }
