package com.siparisYonetim.SiparisYonetimSistemi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping
    public String mainPage(Model model, Principal principal) {
        model.addAttribute("isAuthenticatedMain", principal != null);

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("name", username);
            model.addAttribute("username", username);
        }
        return "/logout/main";
    }
}
