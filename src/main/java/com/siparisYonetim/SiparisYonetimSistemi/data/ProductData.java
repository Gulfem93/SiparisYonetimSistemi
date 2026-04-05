package com.siparisYonetim.SiparisYonetimSistemi.data;

import lombok.*;

import java.io.Serializable;

@Data
public class ProductData implements Serializable {
    private Long id;
    private String name;
    private Long code;
    private Long price;
    private String stock;
}
