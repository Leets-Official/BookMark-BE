package leets.bookmark.domain.user.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResponseMessage {

    GET_USER_INFO_SUCCESS("정보 조회에 성공했습니다."),
    UPDATE_USER_NICKNAME_SUCCESS("닉네임 변경에 성공했습니다."),
    WITHDRAW_USER_SUCCESS("정상적으로 탈퇴되었습니다.");

    private final String message;
}
