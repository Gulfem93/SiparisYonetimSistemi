package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.data.ProductData;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import com.siparisYonetim.SiparisYonetimSistemi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(ControllerConstant.COMPANY_OWNER + ControllerConstant.PRODUCTS)
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;

    public ProductController(ProductService productService, UserRepository userRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String productPage(Model model, Authentication authentication) {
        model.addAttribute("products", productService.getAllProducts(getCompanyName(authentication)));
        model.addAttribute("displayName", getDisplayName(authentication));
        return "company/companyOwner-product";
    }

    @GetMapping("/new")
    public String getProduct(Model model) {
        model.addAttribute("productForm", new ProductData());
        return "products/new";
    }

    @PostMapping("/new")
    public String createProduct(@Valid @ModelAttribute("productForm") ProductData productForm,
                                BindingResult bindingResult,
                                Model model,
                                Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "products/new";
        }

        boolean created = productService.createProduct(productForm, getCompanyName(authentication));

        if (!created) {
            model.addAttribute("error", "Bu ürün kodu zaten kullanılıyor.");
            return "products/new";
        }

        return "redirect:/companyOwner/products";
    }

    @GetMapping("/edit/{code}")
    public String getProductEdit(@PathVariable("code") String code, Model model, Authentication authentication) {
        ProductData productEditForm = productService.getProductByCode(code, getCompanyName(authentication));

        if (productEditForm == null) {
            return "redirect:/companyOwner/products";
        }

        model.addAttribute("productEditForm", productEditForm);
        return "products/edit";
    }

    @PostMapping("/edit")
    public String updateProduct(@Valid @ModelAttribute("productEditForm") ProductData productEditForm,
                                BindingResult bindingResult,
                                Model model,
                                Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "products/edit";
        }

        boolean updated = productService.updateProduct(productEditForm, getCompanyName(authentication));

        if (!updated) {
            model.addAttribute("error", "Bu ürün kodu başka bir üründe kullanılıyor.");
            return "products/edit";
        }

        return "redirect:/companyOwner/products";
    }

    @GetMapping("/delete/{code}")
    public String deleteProduct(@PathVariable("code") String code, Authentication authentication) {
        productService.deleteProductByCode(code, getCompanyName(authentication));
        return "redirect:/companyOwner/products";
    }

    private String getCompanyName(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .map(user -> user.getName())
                .filter(name -> !name.isBlank())
                .orElse(authentication.getName());
    }

    private String getDisplayName(Authentication authentication) {
        return getCompanyName(authentication);
    }
}
