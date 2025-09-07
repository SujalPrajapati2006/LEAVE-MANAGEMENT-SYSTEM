    package com.example.authservice.security;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.JwtException;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    import java.util.Base64;
    import java.util.Date;
    import java.util.List;

    @Component
    public class JwtUtil {

        @Autowired
        private JwtProperties jwtProperties;

        private byte[] getSigningKey() {
            return Base64.getDecoder().decode(jwtProperties.getSecret());
        }

        public String generateToken(String email, String role) {
            Date now = new Date();
            Date expiry = new Date(now.getTime() + jwtProperties.getExpiration());

            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(now)
                    .setExpiration(expiry)
                    .setIssuer(jwtProperties.getIssuer())
                    .claim("authorities", List.of("ROLE_" + role))
                    .signWith(Keys.hmacShaKeyFor(getSigningKey()), SignatureAlgorithm.HS256)
                    .compact();
        }

        public String generateRefreshToken(String email) {
            Date now = new Date();
            Date expiry = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000); // 7 days

            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(now)
                    .setExpiration(expiry)
                    .setIssuer(jwtProperties.getIssuer())
                    .signWith(Keys.hmacShaKeyFor(getSigningKey()), SignatureAlgorithm.HS256)
                    .compact();
        }


        public String extractUsername(String token) {
            return getClaims(token).getSubject();
        }

        public boolean isTokenValid(String token) {
            try {
                Claims claims = getClaims(token);
                return !claims.getExpiration().before(new Date());
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        }

        public Claims getClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(getSigningKey()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        public Date getExpiration(String token) {
            return getClaims(token).getExpiration();
        }

    }
