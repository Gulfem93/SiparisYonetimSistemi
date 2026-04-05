package com.siparisYonetim.SiparisYonetimSistemi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "CART")
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;

    private String price;
    private String date;
    private String note;
}
