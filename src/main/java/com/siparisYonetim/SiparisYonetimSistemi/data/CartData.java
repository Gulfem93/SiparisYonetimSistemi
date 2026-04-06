package com.siparisYonetim.SiparisYonetimSistemi.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartData implements Serializable {
    private Long id;
    private Long customerId;
    private Long productId;
    private Long price;
    private String date;
    private String note;

}
