package com.siparisYonetim.SiparisYonetimSistemi.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasketData implements Serializable {
    private Long id;
    private Long productId;
}
