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
        model.addAttribute("home", "Home");
        if (principal != null) {
            String principalName = principal.getName();
            String displayName = userRepository.findByUsernameOrMail(principalName, principalName)
                    .map(UserModel::getName)
                    .filter(name -> name != null && !name.isBlank())
                    .orElse(principalName);
            model.addAttribute("accountDisplayName", displayName);
        }
        return "home";
    }
}
