package com.siparisYonetim.SiparisYonetimSistemi.repository;

import com.siparisYonetim.SiparisYonetimSistemi.model.CompanyProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyProductRepository extends JpaRepository<CompanyProductModel, Long> {
}
