package com.s2.LogiTrack_S.A.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    // Define la variable de entorno JWT_SECRET (mínimo 32 caracteres) en lugar de dejarla en el código
    @Value("${JWT_SECRET:clave_super_secreta_para_clase_2026}")
    private String secret;
    private final long EXPIRATION = 1000 * 60 * 30; // 30 minutos

    private Key getKey() { return Keys.hmacShaKeyFor(secret.getBytes()); }

    public String generateToken(String username, Long id) {
        return Jwts.builder()
                .setSubject(username)
                .claim("uid", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /** Devuelve los claims si el token es válido; null si no. */
    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}