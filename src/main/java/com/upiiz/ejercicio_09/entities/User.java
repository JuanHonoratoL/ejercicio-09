package com.upiiz.ejercicio_09.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.observation.annotation.ObservationKeyValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Para que se corresponda con la tabla en la base de datos
// Si agregamops Lombok
// Genera setters y getters
@Data
// Genera el constructor sin parámetros
@NoArgsConstructor
// Genera el constructor con parámetros
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Distingue entre mayúsculas y minúsculas
    @Column(nullable = false, name = "full_name")
    // Aclaramos que en JSON viene fullname del cliente
    @JsonProperty("fullname")
    private String fullname;

    public void setPasswordCodificado(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Es cierto, la cuenta no expira
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Es cierto, la cuenta no está bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Es cierto, las credenciales no expiram
        return true;
    }

}
