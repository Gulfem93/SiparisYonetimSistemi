package com.siparisYonetim.SiparisYonetimSistemi.repository;

import com.siparisYonetim.SiparisYonetimSistemi.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Optional<ProductModel> findByCodeAndCompanyName(String code, String companyName);
    List<ProductModel> findAllByCompanyName(String companyName);
    Optional<ProductModel> findByIdAndCompanyName(Long id, String companyName);
    boolean existsByCodeAndCompanyName(String code, String companyName);
}
