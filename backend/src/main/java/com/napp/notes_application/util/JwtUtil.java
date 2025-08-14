package com.napp.notes_application.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret){
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(secretKey)
                .compact();

    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token).getPayload();
    }
    public String getEmail(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpiration(String token){
        return getAllClaimsFromToken(token).getExpiration();
    }


    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
    public boolean isTokenValid(String token, String email) {
        String extractedEmail = getEmail(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }


}
