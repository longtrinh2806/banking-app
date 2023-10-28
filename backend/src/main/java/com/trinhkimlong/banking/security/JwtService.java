package com.trinhkimlong.banking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    Constant constant = new Constant();
    SecretKey key = Keys.hmacShaKeyFor(constant.getSECRET_KEY().getBytes());
    public String generateToken(Authentication authentication) {
        return Jwts
                .builder()
                .claim("email", authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 30 * 60))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        token = token.substring(7);
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }
}
