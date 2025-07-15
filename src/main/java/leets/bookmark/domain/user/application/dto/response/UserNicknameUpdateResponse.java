package leets.bookmark.domain.user.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "닉네임 변경 응답 DTO")
public record UserNicknameUpdateResponse(
        String nickname
) {
}
