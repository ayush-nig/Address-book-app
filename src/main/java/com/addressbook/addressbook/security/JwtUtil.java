package com.addressbook.addressbook.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component  // ✅ Ensure Spring Boot detects this as a Bean
public class JwtUtil {

    private static final String SECRET = "ThisIsASecretKeyThatIsAtLeast32CharactersLong!!!"; // Ensure 32+ characters
    private static final byte[] decodedKey = Base64.getEncoder().encode(SECRET.getBytes());
    private static final SecretKey key = Keys.hmacShaKeyFor(decodedKey);

    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1-hour expiration
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
