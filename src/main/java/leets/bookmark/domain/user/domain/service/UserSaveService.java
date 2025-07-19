package leets.bookmark.domain.user.domain.service;

import leets.bookmark.domain.user.application.mapper.UserMapper;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.repository.UserRepository;
import leets.bookmark.global.auth.oauth2.userinfo.KakaoOAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSaveService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User save(KakaoOAuth2UserInfo userInfo) {
        return userRepository.findByKakaoId(userInfo.getProviderId())
                .orElseGet(() -> userRepository.save(userMapper.toUserEntity(userInfo)));
    }
}
