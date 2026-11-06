package com.upiiz.ejercicio_09.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.upiiz.ejercicio_09.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfigSecurity {
    // Sobreescribir la seguridad por default
    // Security Filter Chain
    // Hacer uso de nuestro filtro
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor
    public ConfigSecurity(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Configuramos que endpoints van a estar disponibles sin autentitación
    // Para all lo demas se requiere autenticacion

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/v1/auth/login").permitAll()
                        .requestMatchers("/public/v1/auth/registro").permitAll()
                        .anyRequest().authenticated() // Clientes, productos, categorias - Enviar el Token para
                                                      // solicitud
                )
                .sessionManagement(sesiones -> sesiones.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    // Son elementos del ecosistema que están disponibles cuando se requieran
    @Bean
    // Authentication Manager
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Authentication Provider
    /*
     * @Bean
     * public AuthenticationProvider authenticationProvider() {
     * DaoAuthenticationProvider daoAuthenticationProvider = new
     * DaoAuthenticationProvider(null);
     * daoAuthenticationProvider.setPasswordEncoder(null);
     * return daoAuthenticationProvider;
     * }
     */

    // Password Encoder
    // User Detail Service - Memory o Base de Datos
    // Si se requiere, busca en el ecosistema de Spring Boot
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
