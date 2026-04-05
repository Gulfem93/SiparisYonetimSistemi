package com.siparisYonetim.SiparisYonetimSistemi.service;

import com.siparisYonetim.SiparisYonetimSistemi.data.UserData;
import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public boolean createUser(UserData userForm) {
        if (userForm == null || userForm.getEmail() == null || userForm.getPassword() == null) {
            return false;
        }

        String email = userForm.getEmail().trim().toLowerCase();
        if (email.isEmpty()) {
            return false;
        }

        if (userRepository.existsByEmail(email)) {
            return false;
        }

        UserModel user = modelMapper.map(userForm, UserModel.class);
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setEmail(email);
        user.setName(userForm.getName());

        userRepository.save(user);
        return true;
    }

    public UserData convertModel2Data(UserModel user) {
        if (Objects.isNull(user)) return null;
        UserData userData = modelMapper.map(user, UserData.class);
        userData.setPassword(null);
        return userData;
    }
}
