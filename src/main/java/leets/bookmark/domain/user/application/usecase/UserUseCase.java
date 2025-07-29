package leets.bookmark.domain.user.application.usecase;

import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;

public interface UserUseCase {
    UserInfoResponse getUserInfo(Long userId);

    void updateNickname(Long userId, String newNickname);

    void withdraw(Long userId);
}
