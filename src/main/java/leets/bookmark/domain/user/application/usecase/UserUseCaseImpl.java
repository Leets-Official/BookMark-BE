package leets.bookmark.domain.user.application.usecase;

import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.application.mapper.UserMapper;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserMapper userMapper;

    @Override
    public UserInfoResponse getUserInfo(User user) {
        return userMapper.toUserInfoResponse(user);
    }
}
