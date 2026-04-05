package com.siparisYonetim.SiparisYonetimSistemi.data;

import lombok.*;

import java.io.Serializable;

@Data
public class CustumerData implements Serializable {
    private Long id;
    private String type;
    private Long phone;
    private String email;
    private String address;
}
