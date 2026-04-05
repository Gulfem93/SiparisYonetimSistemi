package com.siparisYonetim.SiparisYonetimSistemi.config;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String LOGIN_PROCESSING_URL = ControllerConstant.LOGIN;
    private static final String CUSTOMER = ControllerConstant.SIGNUP;
    private static final String API_PREFIX = ControllerConstant.API;
    private static final String API_PROCESSING_URL = API_PREFIX + "/**";

    private static final String[] WEB_WHITELIST = {
            "/css/**", "/js/**", "/images/**", "/webjars/**",
            "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.svg", "/**/*.ico",
            "/favicon.ico", "/home.css"
    };

    @Bean
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(request -> !request.getRequestURI().startsWith(API_PREFIX))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WEB_WHITELIST).permitAll()
                        .requestMatchers(ControllerConstant.HOME, LOGIN_PROCESSING_URL, CUSTOMER, ControllerConstant.ERROR).permitAll()
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
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
