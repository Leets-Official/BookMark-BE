package leets.bookmark.domain.user.application.usecase;

import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.application.dto.response.UserNicknameUpdateResponse;
import leets.bookmark.domain.user.application.mapper.UserMapper;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserUpdateService userUpdateService;
    private final UserMapper userMapper;

    @Override
    public UserInfoResponse getUserInfo(User user) {
        return userMapper.toUserInfoResponse(user);
    }

    @Override
    public UserNicknameUpdateResponse updateNickname(User user, String newNickname) {
        return userMapper.toUserNicknameUpdateResponse(userUpdateService.updateNickname(user, newNickname));
    }
}
