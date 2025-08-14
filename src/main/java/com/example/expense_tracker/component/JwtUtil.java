package com.example.expense_tracker.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.security.jwt.secret}") private String secret;
    @Value("${app.security.jwt.ttl-min}") private Long ttlMin;

    public String generateToken(String subject, Collection<? extends GrantedAuthority> roles) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + ttlMin * 60 * 1000);
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", roles.stream().map(GrantedAuthority::getAuthority).toList())
                .setIssuedAt(now).setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
    }

}
