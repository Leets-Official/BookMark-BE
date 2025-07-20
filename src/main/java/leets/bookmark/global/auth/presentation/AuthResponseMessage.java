package leets.bookmark.global.auth.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthResponseMessage {

    JWT_TOKEN_REISSUE_SUCCESS("토큰 재발급에 성공했습니다.");

    private final String message;
}
