package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.data.UserData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(ControllerConstant.LOGIN)
public class LoginController {
    @GetMapping
    public String getLogin(Model model,
                           @RequestParam(required = false) String error,
                           @RequestParam(required = false) String logout) {
        if (error != null) {
            model.addAttribute("errorMessage", "An error occured during login. Please try again.");
        }

        if (logout != null) {
            model.addAttribute("successMessage", "You have been successfully logged out.");
        }

        model.addAttribute("loginForm", new UserData());
        return "login";
    }
}
