package com.siparisYonetim.SiparisYonetimSistemi.model;

import com.siparisYonetim.SiparisYonetimSistemi.data.CustomerData;
import com.siparisYonetim.SiparisYonetimSistemi.data.ProductData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "CART")
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductModel product;

    private Long price;
    private String date;
    private String note;
}
