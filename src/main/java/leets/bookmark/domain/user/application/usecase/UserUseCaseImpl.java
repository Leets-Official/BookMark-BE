package leets.bookmark.domain.user.application.usecase;

import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.application.mapper.UserMapper;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import leets.bookmark.domain.user.domain.service.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserGetService userGetService;
    private final UserUpdateService userUpdateService;
    private final UserMapper userMapper;

    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userGetService.findById(userId);
        return userMapper.toUserInfoResponse(user);
    }

    @Override
    public void updateNickname(Long userId, String newNickname) {
        User user = userGetService.findById(userId);
        userUpdateService.updateNickname(user, newNickname);
    }
}
