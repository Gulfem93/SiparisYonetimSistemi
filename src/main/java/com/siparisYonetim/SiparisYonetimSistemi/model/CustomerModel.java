package com.siparisYonetim.SiparisYonetimSistemi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "CUSTOMER")
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Long phone;
    @Email
    @NotBlank
    private String email;
    private String address;
}
