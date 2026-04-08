package com.siparisYonetim.SiparisYonetimSistemi.repository;

import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByUsernameOrMail(String username, String mail);
    boolean existsByUsername(String username);
}
