package com.upiiz.ejercicio_09.controllers;

import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityReturnValueHandler;

import com.upiiz.ejercicio_09.entities.User;
import com.upiiz.ejercicio_09.services.JwtService;
import com.upiiz.ejercicio_09.services.UserService;

@RestController
@RequestMapping("/public/v1/auth")
public class AuthController {
    // Estos endpoints NO REQUIEREN ESTAR AUTENTICADOS
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // Registro de un usuario - POST
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody User user) {
        // Verificar que no exista otro nombre de usuario igual
        if (userService.getUserByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body(
                    Map.of("estado", 409,
                            "mensaje", "El usuario ya existe"));
        } else { // Verificar que sí se pudo registrar el usuario
            return ResponseEntity.status(201).body(
                    Map.of("estado", 201,
                            "mensaje", "Usuario registrado",
                            "usuario", userService.savUser(user)));
        }
    }

    // Login de un usuario - POST - Regresamos el JWT = Token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User usuario = userService.getUserByUsername(user.getUsername()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(400).body(
                    Map.of("estado:", 400,
                            "mensaje", "Usuario no encontrado"));
        } else {
            return ResponseEntity.status(200).body(
                    Map.of("estado", 200,
                            "mensaje", "Usuario logueado",
                            "token", jwtService.generateJwtToken(usuario.getUsername())));
        }
    }

    @GetMapping("/productos")
    public String productos() {
        return "productos";
    }

    @GetMapping("/permiso")
    public String saludar() {
        return "Hola";
    }

}
