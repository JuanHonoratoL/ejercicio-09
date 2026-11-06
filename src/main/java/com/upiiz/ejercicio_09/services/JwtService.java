package com.upiiz.ejercicio_09.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

// Genera un Bean - Frijolito - Es un elemento que se crea en el ecosistema
// Si se requiere se busca, se encuentra y es usado
@Service
public class JwtService {
    // Duración de un dia = 60 segundos * 60 minutos * 24 horas * 1000 para
    // convertirlo a milisegundos
    private final int TIEMPO_PARA_EXPIRAR = 60 * 60 * 24 * 1000;
    private String LLAVE_SECRETA = "Mi_llave_muy_muy_secreta_que_no_la_debo_de_compartir";

    // Generar el token
    // No registramos Claims = Nombre completo, Rol, telefono, etc.
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIEMPO_PARA_EXPIRAR))
                .signWith(obtenerLlave(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtener el usuario del Token - Decodifica
    public String obtenerNombreUsuario(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(obtenerLlave())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Obtener si el token es válido
    // 1. No está vencido
    // 2. Nombre de usuario corresponde al token
    public boolean tokenValido(String userName, String token) {
        return obtenerNombreUsuario(token).equals(userName) && !estaExpirado(token);
    }

    // Verificar si está expirado
    public boolean estaExpirado(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(obtenerLlave())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    // Generar una llave para firmarlo
    private Key obtenerLlave() {
        return Keys.hmacShaKeyFor(LLAVE_SECRETA.getBytes());
    }

}
