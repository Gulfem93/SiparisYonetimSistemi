package com.siparisYonetim.SiparisYonetimSistemi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "COMPANY_PRODUCT")
public class CompanyProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "product_id")
    private ProductModel product;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id")
    private UserModel user;
}

