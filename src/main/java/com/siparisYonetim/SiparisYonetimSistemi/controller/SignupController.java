package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.data.UserData;
import com.siparisYonetim.SiparisYonetimSistemi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ControllerConstant.SIGNUP)
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignup(Model model) {
        model.addAttribute("signupForm", new UserData());
        return "signup";
    }

    @PostMapping
    public String postSignup(@Valid @ModelAttribute("signupForm") UserData signupForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        boolean isboolean = userService.createUser(signupForm);
        if (isboolean) {
            return "redirect:/";
        }else  {
            model.addAttribute("signup", "signup");
            return "signup";
        }
    }
}
