package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping(ControllerConstant.HOME)
public class HomeController {
    private static final String ANONYMOUS_USER = "anonymousUser";

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getHome(@RequestParam(value = "role", defaultValue = "user") String role,
                          Authentication authentication,
                          Model model) {
        boolean hasCustomerRole = hasAuthority(authentication, "ROLE_CUSTOMER");
        String requestedRole = "customer".equalsIgnoreCase(role) ? "customer" : "user";
        String currentUsername = resolveCurrentUsername(authentication);
        String currentUserDisplay = resolveCurrentUserDisplay(currentUsername);
        String currentAvatar = resolveAvatarInitial(currentUserDisplay, isCustomerRole(requestedRole, hasCustomerRole));

        // Customer hesabi varsa admin paneline gecis zorla kapatilir.
        String normalizedRole = hasCustomerRole ? "customer" : requestedRole;
        boolean isCustomer = "customer".equals(normalizedRole);

        model.addAttribute("home", "SiparisYonetimSistemi");
        model.addAttribute("homeRole", normalizedRole);
        model.addAttribute("isCustomer", isCustomer);
        model.addAttribute("canSwitchRole", !hasCustomerRole);
        model.addAttribute("panelTitle", isCustomer ? "Musteri Alani" : "Kullanici Paneli");
        model.addAttribute("panelSubtitle", isCustomer ? "Siparis ve hesap takibi" : "Siparis Yonetim Sistemi operasyonu");
        model.addAttribute("panelBadge", isCustomer ? "Musteri Hesabi" : "Yonetici Paneli");
        model.addAttribute("panelAvatar", currentAvatar);
        model.addAttribute("currentUserDisplay", currentUserDisplay);
        model.addAttribute("currentUsername", currentUsername);
        return "home";
    }

    private boolean hasAuthority(Authentication authentication, String authority) {
        if (authentication == null || authentication.getAuthorities() == null) {
            return false;
        }

        return authentication.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> authority.equalsIgnoreCase(grantedAuthority.getAuthority()));
    }

    private String resolveCurrentUsername(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return null;
        }

        String principalName = authentication.getName();
        if (ANONYMOUS_USER.equalsIgnoreCase(principalName)) {
            return null;
        }

        return principalName;
    }

    private String resolveCurrentUserDisplay(String username) {
        if (username == null || username.isBlank()) {
            return "Misafir";
        }

        Optional<UserModel> userOptional = userRepository.findByEmail(username);
        if (userOptional.isPresent() && userOptional.get().getName() != null && !userOptional.get().getName().isBlank()) {
            return userOptional.get().getName();
        }

        return username;
    }

    private String resolveAvatarInitial(String displayName, boolean isCustomer) {
        if (displayName != null && !displayName.isBlank()) {
            return displayName.substring(0, 1).toUpperCase(Locale.ROOT);
        }

        return isCustomer ? "M" : "Y";
    }

    private boolean isCustomerRole(String requestedRole, boolean hasCustomerRole) {
        if (hasCustomerRole) {
            return true;
        }
        return "customer".equals(requestedRole);
    }

}
