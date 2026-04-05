package com.siparisYonetim.SiparisYonetimSistemi.data;

import lombok.*;

import java.io.Serializable;

@Data
public class CartData implements Serializable {
    private Long id;
    private Long custumerId;
    private Long productId;
    private String price;
    private String date;
    private String note;

}
