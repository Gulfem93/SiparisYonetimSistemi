package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.model.CompanyModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(ControllerConstant.COMPANY_OWNER)
public class CompanyOwnerController {
    private final UserRepository userRepository;

    public CompanyOwnerController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getCompanyOwner(Model model, CompanyModel companyModel, Principal principal) {
        model.addAttribute("companyForm", companyModel);
        if (principal != null) {
            String displayName = userRepository.findByUsername(principal.getName())
                    .map(user -> user.getName())
                    .filter(name -> !name.isBlank())
                    .orElse(principal.getName());
            model.addAttribute("displayName", displayName);
        }
        return "company/companyOwner";
    }
}
