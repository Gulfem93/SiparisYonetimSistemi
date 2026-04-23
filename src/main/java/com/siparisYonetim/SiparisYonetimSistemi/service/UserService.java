package com.siparisYonetim.SiparisYonetimSistemi.service;

import com.siparisYonetim.SiparisYonetimSistemi.data.UserData;
import com.siparisYonetim.SiparisYonetimSistemi.model.UserModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public boolean createUser(UserData userForm) {
        if (userRepository.findByUsername(userForm.getUsername()).isPresent()) {
            return false;
        }
        UserModel userModel = modelMapper.map(userForm, UserModel.class);
        userModel.setPassword(passwordEncoder.encode(userForm.getPassword()));
        userModel.setUsername(userForm.getMail());
        userModel.setName(userForm.getName());
        userModel.setMail(userForm.getMail());
        userRepository.save(userModel);
        return true;
    }
}
