package com.siparisYonetim.SiparisYonetimSistemi.data;

import lombok.*;

import java.io.Serializable;

@Data
public class UserData implements Serializable {
    public Long id;
    public String name;
    public String email;
    public String password;
}
