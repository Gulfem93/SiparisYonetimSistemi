package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.model.CompanyModel;
import com.siparisYonetim.SiparisYonetimSistemi.model.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ControllerConstant.COMPANY_OWNER)
public class CompanyOwnerController {
    @GetMapping
    public String getCompanyOwner(Model model, CompanyModel companyModel) {
        model.addAttribute("companyForm", companyModel);
        return "company/companyOwner";
    }
}
