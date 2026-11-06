package com.upiiz.ejercicio_09.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.upiiz.ejercicio_09.services.UserService;

@Service
public class CustomUserDetailService implements UserDetailsService {
    // hacer uso del servicio
    final UserService userService;

    // Constructor
    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    // Implementamos el método que se sobreescribe.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Obtener el usuario de la base de datos
        return userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
