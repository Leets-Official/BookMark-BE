package leets.bookmark.domain.user.application.dto.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        Long id,
        String kakaoId,
        String email,
        String nickname,
        String profileImage,
        String role
) {
}
