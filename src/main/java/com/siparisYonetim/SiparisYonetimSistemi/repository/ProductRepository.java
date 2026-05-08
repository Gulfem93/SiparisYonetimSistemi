package com.siparisYonetim.SiparisYonetimSistemi.repository;

import com.siparisYonetim.SiparisYonetimSistemi.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Optional<ProductModel> findByCode(String code);
    Optional<ProductModel> findAllById(Long productId);
    boolean existsByCode(String code);
}
