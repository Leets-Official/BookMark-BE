package leets.bookmark.domain.user.application.dto.response;

import lombok.Builder;

@Builder
public record UserKakaoLoginResponse(
        Long userId,
        String profileImage,
        String jwtAccessToken,
        String jwtRefreshToken
) {
}
