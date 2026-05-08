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
    private static final String PRODUCT_URL = ControllerConstant.PRODUCTS;
    private static final String CUSTOMER_URL = ControllerConstant.CUSTOMER;
    private static final String COMPANY_URL = ControllerConstant.COMPANY;
    private static final String COMPANY_PRODUCT_URL = ControllerConstant.COMPANY_PRODUCT;
    private static final String COMPANY_OWNER_URL = ControllerConstant.COMPANY_OWNER;
    private static final String CUSTOMER_OWNER_URL = ControllerConstant.CUSTOMER_OWNER;
    private static final String API = ControllerConstant.API;

    private static final String [] WEB_WHITELIST = {"/css/**", "/images/**", "/js/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(request -> !request.getRequestURI().startsWith(API))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(LOGIN_URL, SIGNUP_URL,
                                LOGOUT, HOME_URL,
                                CUSTOMER_URL, COMPANY_URL, PRODUCT_URL, COMPANY_PRODUCT_URL, COMPANY_OWNER_URL, CUSTOMER_OWNER_URL).permitAll()
                        .requestMatchers(API).permitAll()
                        .requestMatchers(WEB_WHITELIST).permitAll()
                        .requestMatchers("/companyOwner/**").hasRole("COMPANY")
                        .requestMatchers("/customerOwner/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage(LOGIN_URL)
                        .loginProcessingUrl(LOGIN_URL)
                        .failureUrl(LOGIN_URL + "?error=true")
                        .successHandler((request, response, authentication) -> {
                            boolean isCompany = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_COMPANY"));

                            boolean isCustomer = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"));


                            if (isCompany) {
                                response.sendRedirect(ControllerConstant.COMPANY_OWNER);
                            } else if (isCustomer) {
                                response.sendRedirect(ControllerConstant.HOME);
                            } else {
                                response.sendRedirect(ControllerConstant.HOME);
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl(LOGOUT)
                        .logoutSuccessUrl(HOME_URL)
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
                    .roles(user.getAccountType().name())
                    .build();
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

