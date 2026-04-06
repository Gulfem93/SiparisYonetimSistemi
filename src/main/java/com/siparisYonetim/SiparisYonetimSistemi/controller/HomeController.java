package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ControllerConstant.HOME)
public class HomeController {
    @GetMapping
    public String getHome(Model model) {
        model.addAttribute("home", "Home");
        return "home";
    }

}
