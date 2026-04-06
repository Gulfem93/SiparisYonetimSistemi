package com.siparisYonetim.SiparisYonetimSistemi.model;

import com.siparisYonetim.SiparisYonetimSistemi.data.ProductData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BASKET")
@Getter
@Setter

public class BasketModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductData product_Id;
}
