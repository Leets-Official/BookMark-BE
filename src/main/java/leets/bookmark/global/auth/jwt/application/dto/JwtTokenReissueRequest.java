package leets.bookmark.global.auth.jwt.application.dto;

import jakarta.validation.constraints.NotBlank;

public record JwtTokenReissueRequest(
        @NotBlank String refreshToken
) {
}
