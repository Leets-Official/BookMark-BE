package leets.bookmark.global.auth.jwt.application.dto;

import lombok.Builder;

@Builder
public record JwtTokenDto(
        String accessToken,
        String refreshToken
) {
}