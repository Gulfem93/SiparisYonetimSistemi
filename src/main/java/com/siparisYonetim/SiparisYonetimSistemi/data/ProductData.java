package com.siparisYonetim.SiparisYonetimSistemi.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductData implements Serializable {
    private Long id;
    private String name;
    private String code;
    private Long price;
    private String stock;

}
