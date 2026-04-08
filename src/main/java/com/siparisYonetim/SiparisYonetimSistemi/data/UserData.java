package com.siparisYonetim.SiparisYonetimSistemi.data;
import com.siparisYonetim.SiparisYonetimSistemi.enums.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserData implements Serializable {
    private Long id;
    private String username;
    private String name;
    @Email
    @NotBlank
    private String mail;
    @NotBlank
    private String password;

    @NotNull
    private AccountType accountType;

}
