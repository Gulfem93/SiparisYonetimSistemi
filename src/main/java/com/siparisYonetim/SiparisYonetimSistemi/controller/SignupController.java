package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.data.UserData;
import com.siparisYonetim.SiparisYonetimSistemi.security.service.AutoLoginService;
import com.siparisYonetim.SiparisYonetimSistemi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(ControllerConstant.SIGNUP)
public class SignupController {
    private final UserService userService;
    private final AutoLoginService autoLoginService;

    public SignupController(UserService userService, AutoLoginService autoLoginService) {
        this.userService = userService;
        this.autoLoginService = autoLoginService;
    }

    @GetMapping
    public String getSignup(Model model) {
        model.addAttribute("signup", "signup");
        return "signup";
    }

    @PostMapping
    public String postSignup(
            @Valid @ModelAttribute UserData userForm,
            @RequestParam("rePassword") String rePassword,
            Model model) {

        if (userForm == null || userForm.getPassword() == null || !userForm.getPassword().equals(rePassword)) {
            model.addAttribute("signup", "signup");
            model.addAttribute("errorMessage", "Sifreler uyusmuyor.");
            return "signup";
        }

        boolean isCreated = userService.createUser(userForm);
        if (isCreated) {
            autoLoginService.autoLogin(userForm.getEmail(), userForm.getPassword());
            return "redirect:/";
        } else {
            model.addAttribute("signup", "signup");
            model.addAttribute("errorMessage", "Kayit olusturulamadi. E-posta zaten kullaniliyor olabilir.");
            return "signup";
        }

    }
}
