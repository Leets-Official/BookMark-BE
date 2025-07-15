package leets.bookmark.domain.user.application.usecase;

import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.application.dto.response.UserNicknameUpdateResponse;
import leets.bookmark.domain.user.domain.entity.User;

public interface UserUseCase {
    UserInfoResponse getUserInfo(User user);

    UserNicknameUpdateResponse updateNickname(User user, String newNickname);
}
