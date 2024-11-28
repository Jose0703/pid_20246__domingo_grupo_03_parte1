package com.example.proyectogrupo3.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Token {

    private final static String TOKEN_SECRETO = "02VGC0hf1qembHBEm6RQvbGpVf8T8YeP";
    private final static Long TOKEN_DURATION = 6_000L;

    public static String crearToken(String user, String email) {
        long expiracionTiempo = TOKEN_DURATION * 100L;
        Date expiracionFecha = new Date(System.currentTimeMillis()+ expiracionTiempo);

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", user);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expiracionFecha)
                .addClaims(map)
                .signWith(Keys.hmacShaKeyFor(TOKEN_SECRETO.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuth(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(TOKEN_SECRETO.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            return null;
        }
    }
}
