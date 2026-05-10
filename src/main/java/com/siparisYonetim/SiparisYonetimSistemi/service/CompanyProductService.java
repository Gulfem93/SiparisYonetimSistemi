package com.siparisYonetim.SiparisYonetimSistemi.service;

import com.siparisYonetim.SiparisYonetimSistemi.model.CompanyProductModel;
import com.siparisYonetim.SiparisYonetimSistemi.model.ProductModel;
import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.CompanyProductRepository;
import com.siparisYonetim.SiparisYonetimSistemi.repository.ProductRepository;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CompanyProductRepository companyProductRepository;

    public CompanyProductService(ProductRepository productRepository, UserRepository userRepository, CompanyProductRepository companyProductRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.companyProductRepository = companyProductRepository;
    }

    boolean createProductToCompany(
            Long productId,
            Long userId
    ) {

        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Ürün bulunamadı"));

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Kullanıcı bulunamadı"));

        CompanyProductModel companyProductModel =
                new CompanyProductModel();

        companyProductModel.setProduct(product);
        companyProductModel.setUser(user);
        companyProductRepository.save(companyProductModel);
        return true;
    }


}
