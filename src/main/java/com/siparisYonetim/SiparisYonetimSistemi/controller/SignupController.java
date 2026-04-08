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
    public String postSignup(Model model,
                             @Valid @ModelAttribute("signupForm") UserData userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup";
        }

        boolean isCreated = userService.createUser(userForm);
        if (isCreated) {
            return "redirect:/signup";
        } else {
            model.addAttribute("errorMessage", "Kayit olusturulamadi. Email zaten kayitli olabilir.");
            return "signup";
        }
    }
}
