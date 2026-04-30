package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(ControllerConstant.HOME)
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getHome(Model model, Principal principal) {
        model.addAttribute("isAuthenticated", principal != null);
        model.addAttribute("isAuthenticatedMain", principal != null);

        if (principal != null) {
            String username = principal.getName();
            UserModel userModel = userRepository.findByUsername(username).orElseThrow();

            model.addAttribute("userName", userModel.getName());
            model.addAttribute("name", userModel.getName());
        }

        return "home";
    }

}
