package leets.bookmark.domain.user.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(description = "닉네임 변경 요청 DTO")
public record UserNicknameUpdateRequest(
        @NotBlank String nickname
) {
}
