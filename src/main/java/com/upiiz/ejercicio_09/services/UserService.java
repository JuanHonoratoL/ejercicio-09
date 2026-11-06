package com.upiiz.ejercicio_09.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.upiiz.ejercicio_09.entities.User;
import com.upiiz.ejercicio_09.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor
    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Recuperarlo por el nombre de usuario
    public Optional<User> getUserByUsername(String username) {
        // Verifica la clave y usuario coinciden
        return userRepository.findByUsername(username);
    }

    // Guardar un usuario
    public User savUser(User user) {
        // IMPORTANTE - Encriptar o codificar la clave.
        user.setPasswordCodificado(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
