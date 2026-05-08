package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.model.CustomerModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ControllerConstant.CUSTOMER)
public class CustomerController {
    @GetMapping
    public String getCustomer(Model model, CustomerModel customerModel) {
        model.addAttribute("userForm", customerModel);
        return "customer/customer";
    }
}
