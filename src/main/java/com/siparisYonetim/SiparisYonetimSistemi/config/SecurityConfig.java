package com.siparisYonetim.SiparisYonetimSistemi.config;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private static final String LOGIN_URL = ControllerConstant.LOGIN;
    private static final String SIGNUP_URL = ControllerConstant.SIGNUP;
    private static final String LOGOUT = ControllerConstant.LOGOUT;
    private static final String HOME_URL = ControllerConstant.HOME;
    private static final String API = ControllerConstant.API;

    private static final String [] WEB_WHITELIST = {"/css/**", "/images/**", "/js/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(request -> !request.getRequestURI().startsWith(API))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(LOGIN_URL, SIGNUP_URL, LOGOUT, HOME_URL).permitAll()
                        .requestMatchers(API).permitAll()
                        .requestMatchers(WEB_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage(LOGIN_URL)
                        .failureUrl(LOGIN_URL + "?error=true")
                        .defaultSuccessUrl(HOME_URL, true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl(LOGOUT + "?logout=true")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            UserModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            String principal = user.getUsername();
            return User.withUsername(principal)
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
