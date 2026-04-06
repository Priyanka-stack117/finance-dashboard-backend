package com.finance.dashboard.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey123";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ✅ Generate token
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey(), SignatureAlgorithm.HS256)   // ✅ FIXED
                .compact();
    }

    // ✅ Extract username
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Extract role
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // ✅ Common method
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()        // ✅ FIXED
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}