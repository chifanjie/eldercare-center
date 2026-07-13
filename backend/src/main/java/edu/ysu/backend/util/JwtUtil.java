package edu.ysu.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static volatile String configuredSigningKey;

    public JwtUtil(@Value("${security.jwt.secret}") String configured) {
        configuredSigningKey = requireValue(configured);
    }

    private static String requireValue(String configured) {
        if (configured == null || configured.trim().isEmpty()) {
            throw new IllegalStateException("JWT signing key is required");
        }
        return configured;
    }

    private static String requiredSigningKey() {
        String configured = configuredSigningKey;
        if (configured == null || configured.trim().isEmpty()) {
            configured = System.getenv("JWT_SECRET");
        }
        return requireValue(configured);
    }

    public static String createJWT(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 24L * 60L * 60L * 1000L))
                .signWith(SignatureAlgorithm.HS256, requiredSigningKey())
                .compact();
    }

    public static Claims parseJWT(String token) {
        return Jwts.parser()
                .setSigningKey(requiredSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public static String createCustomJWT(String username, long time) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + time))
                .signWith(SignatureAlgorithm.HS256, requiredSigningKey())
                .compact();
    }

    public static boolean isTokenExpired(String token) {
        return parseJWT(token).getExpiration().before(new Date());
    }
}
