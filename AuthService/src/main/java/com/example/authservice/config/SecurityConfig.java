    package com.example.authservice.config;

    import com.example.authservice.security.JwtFilter;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public class SecurityConfig {

        @Autowired
        private JwtFilter jwtFilter;

        @Bean
        public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration configuration
        ) throws Exception {
             return configuration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(
                            auth -> auth

                                    .requestMatchers(
                                            "/auth/users/login",
                                            "/auth/users/register",
                                            "/auth/users/refresh-token",
                                            "/auth/users/change-password",
                                            "/auth/users/forgot-password",
                                            "/auth/users/reset-password"
                                    ).permitAll()

                                    .requestMatchers("auth/users/{id}").hasRole("ADMIN")

                                    .requestMatchers("/auth/users/**").authenticated()

                                    .anyRequest().authenticated()
                    )
                    .sessionManagement(sess
                            -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


            return http.build();
        }
    }