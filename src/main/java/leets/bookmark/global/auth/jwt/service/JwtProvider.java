package leets.bookmark.global.auth.jwt.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.jwt.application.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationMs;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationMs;

    public JwtTokenDto createToken(Object payload) {
        User user = (User) payload;
        long now = new Date().getTime();

        // Access Token 생성
        String accessToken = Jwts.builder()
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setSubject("accessToken")
                .setExpiration(new Date(now + accessTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .claim("id", user.getId())
                .setSubject("refreshToken")
                .setExpiration(new Date(now + refreshTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return new JwtTokenDto(accessToken, refreshToken);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT Token: {}", e.getMessage());
        }
        return false;
    }

    public Long extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
    }
}
