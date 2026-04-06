package com.siparisYonetim.SiparisYonetimSistemi.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerData implements Serializable {
    private Long id;
    private String name;
    private String type;
    private Long phone;
    private String email;
    private String address;

}
