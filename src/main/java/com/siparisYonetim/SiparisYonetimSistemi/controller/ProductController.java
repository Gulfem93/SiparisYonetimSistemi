package com.siparisYonetim.SiparisYonetimSistemi.controller;

import com.siparisYonetim.SiparisYonetimSistemi.constant.ControllerConstant;
import com.siparisYonetim.SiparisYonetimSistemi.data.ProductData;
import com.siparisYonetim.SiparisYonetimSistemi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(ControllerConstant.COMPANY_OWNER + ControllerConstant.PRODUCTS)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
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
                                Model model) {

        if (bindingResult.hasErrors()) {
            return "products/new";
        }

        boolean created = productService.createProduct(productForm);

        if (!created) {
            model.addAttribute("error", "Bu ürün kodu zaten kullanılıyor.");
            return "products/new";
        }

        return "redirect:/companyOwner/products";
    }

    @GetMapping("/edit/{code}")
    public String getProductEdit(@PathVariable("code") String code, Model model) {
        ProductData productEditForm = productService.getProductByCode(code);

        if (productEditForm == null) {
            return "redirect:/companyOwner/products";
        }

        model.addAttribute("productEditForm", productEditForm);
        return "products/edit";
    }

    @PostMapping("/edit")
    public String updateProduct(@Valid @ModelAttribute("productEditForm") ProductData productEditForm,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            return "products/edit";
        }

        boolean updated = productService.updateProduct(productEditForm);

        if (!updated) {
            model.addAttribute("error", "Bu ürün kodu başka bir üründe kullanılıyor.");
            return "products/edit";
        }

        return "redirect:/companyOwner/products";
    }

    @GetMapping("/delete/{code}")
    public String deleteProduct(@PathVariable("code") String code) {
        productService.deleteProductByCode(code);
        return "redirect:/companyOwner/products";
    }
}
