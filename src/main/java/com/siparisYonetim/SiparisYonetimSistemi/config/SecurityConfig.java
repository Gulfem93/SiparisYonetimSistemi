package com.siparisYonetim.SiparisYonetimSistemi.config;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String LOGIN_PROCESSING_URL = ControllerConstant.LOGIN;
    private static final String REGISTER = ControllerConstant.SIGNUP;
    private static final String API_PREFIX = ControllerConstant.API;
    private static final String API_PROCESSING_URL = API_PREFIX + "/**";
    private static final String[] WEB_WHITELIST = {"/css/**", "/images/**", "/js/**", "/favicon.ico"};
    private static final String[] API_WHITELIST = {ControllerConstant.API + ControllerConstant.AUTH + "/**"};

    @Bean
    @Order(1)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(request -> !request.getRequestURI().startsWith(API_PREFIX))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WEB_WHITELIST).permitAll()
                        .requestMatchers(ControllerConstant.HOME, LOGIN_PROCESSING_URL, REGISTER, ControllerConstant.ERROR).permitAll()
                        .requestMatchers(API_PROCESSING_URL).denyAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage(LOGIN_PROCESSING_URL)
                        .failureUrl(LOGIN_PROCESSING_URL + "?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl(LOGIN_PROCESSING_URL + "?logout=true")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage(ControllerConstant.ERROR))
                .sessionManagement(sess -> {
                    sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                    sess.maximumSessions(1);
                })
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher(API_PROCESSING_URL)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(API_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            UserModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            String principal = (user.getUsername() != null && !user.getUsername().isBlank())
                    ? user.getUsername()
                    : user.getMail();
            return User.withUsername(principal)
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        };
    }
}

