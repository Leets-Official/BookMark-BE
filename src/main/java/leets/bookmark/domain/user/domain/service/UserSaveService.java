package leets.bookmark.domain.user.domain.service;

import leets.bookmark.domain.user.application.mapper.UserMapper;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.repository.UserRepository;
import leets.bookmark.global.auth.oauth2.application.dto.response.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSaveService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User save(KakaoUserInfoResponse userInfo) {
        return userRepository.findByKakaoId(userInfo.id().toString())
                .orElseGet(() -> userRepository.save(userMapper.toUser(userInfo)));
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
