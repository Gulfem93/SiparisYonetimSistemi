package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;

@Controller
@RequestMapping(ControllerConstant.HOME)
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getHome(Model model, Principal principal) {
        boolean isAuthenticated = principal != null;
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAuthenticatedMain", isAuthenticated);

        if (isAuthenticated) {
            model.addAttribute("products", productService.getAllProducts());
            String username = principal.getName();
            model.addAttribute("userName", username);
            model.addAttribute("name", username);
            model.addAttribute("username", username);
        } else {
            model.addAttribute("products", Collections.emptyList());
        }

        return "home";
    }

}
