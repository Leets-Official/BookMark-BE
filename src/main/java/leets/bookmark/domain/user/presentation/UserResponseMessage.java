package leets.bookmark.domain.user.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserResponseMessage {
    // UserController 관련
    GET_USER_INFO_SUCCESS("정보 조회에 성공했습니다.");

    private final String message;
}
