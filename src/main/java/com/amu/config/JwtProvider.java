package com.amu.config;

import com.amu.exception.JwtExpiredException;
import com.amu.exception.JwtProcessingException;
import com.amu.exception.JwtValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class JwtProvider {
    private final SecretKey key;

    public JwtProvider() {
        this.key = Keys.hmacShaKeyFor(JWTConstant.JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication auth) {
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWTConstant.JWT_EXPIRATION))
                .compact();

        return token;
    }
    // Trong JwtProvider
    public String getEmailFromJwtToken(String token) {
        try {
            // Xử lý token nếu có Bearer prefix
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Lấy subject thay vì claim "email" vì bạn đã set subject trong generateToken()
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            // Nếu muốn lấy email cả khi token hết hạn
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new JwtValidationException("Invalid token", e);
        }
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException("Token expired", e);
        } catch (JwtException e) {
            throw new JwtValidationException("Invalid token", e);
        }
    }
}
