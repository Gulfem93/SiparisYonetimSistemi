package com.siparisYonetim.SiparisYonetimSistemi.service;

import com.siparisYonetim.SiparisYonetimSistemi.data.UserData;
import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository,  ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public boolean createUser(UserData userData) {
        if (userData == null) {
            return false;
        }
        if (userData.getMail() == null || userData.getPassword() == null) {
            return false;
        }
        String normalizedMail = userData.getMail().trim();
        if (normalizedMail.isBlank() || userData.getPassword().isBlank()) {
            return false;
        }
        if (userRepository.existsByUsername(normalizedMail)) {
            return false;
        }

        try {
            UserModel user = modelMapper.map(userData, UserModel.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setName(user.getName());
            user.setMail(normalizedMail);
            user.setUsername(normalizedMail);
            userRepository.save(user);
            return true;
        } catch (RuntimeException ex) {
            LOGGER.warn("User could not be created for mail: {}", normalizedMail, ex);
            return false;
        }

    }
}
