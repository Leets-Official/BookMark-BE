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

    public static final String CLAIM_ID = "id";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLE = "role";
    public static final String SUBJECT_ACCESS_TOKEN = "accessToken";
    public static final String SUBJECT_REFRESH_TOKEN = "refreshToken";

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationMs;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationMs;

    public JwtTokenDto createToken(Object payload) {
        User user = (User) payload;

        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);

        return new JwtTokenDto(accessToken, refreshToken);
    }

    public String createAccessToken(Object payload) {
        User user = (User) payload;
        long now = new Date().getTime();

        return Jwts.builder()
                .claim(CLAIM_ID, user.getId())
                .claim(CLAIM_EMAIL, user.getEmail())
                .claim(CLAIM_ROLE, user.getRole())
                .setSubject(SUBJECT_ACCESS_TOKEN)
                .setExpiration(new Date(now + accessTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String createRefreshToken(Object payload) {
        User user = (User) payload;
        long now = new Date().getTime();

        return Jwts.builder()
                .claim(CLAIM_ID, user.getId())
                .setSubject(SUBJECT_REFRESH_TOKEN)
                .setExpiration(new Date(now + refreshTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("유효하지 않은 JWT 토큰입니다: {}", e.getMessage());
        }
        return false;
    }

    public Long extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(CLAIM_ID, Long.class);
    }

    public long extractTokenRemainingTime(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .getTime() - System.currentTimeMillis();
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("유효기간을 추출하지 못했습니다: {}", e.getMessage());
            return -1;
        }
    }
}
