package leets.bookmark.global.auth.jwt.application.mapper;

import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.jwt.application.dto.JwtTokenDto;
import org.springframework.stereotype.Component;

@Component
public class JwtMapper {

    public JwtTokenDto toJwtTokenDto(User user) {
        return JwtTokenDto.builder()
                .accessToken(user.getJwtAccessToken())
                .refreshToken(user.getJwtRefreshToken())
                .build();
    }

    public JwtTokenDto toJwtTokenDto(String accessToken, String refreshToken) {
        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
