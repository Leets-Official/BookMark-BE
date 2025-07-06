package leets.bookmark.domain.user.domain.service;

import jakarta.transaction.Transactional;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.entity.enums.Role;
import leets.bookmark.domain.user.domain.repository.UserRepository;
import leets.bookmark.global.auth.oauth2.userinfo.KakaoOAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSaveService {

    private final UserRepository userRepository;

    @Transactional
    public User save(KakaoOAuth2UserInfo userInfo) {
        return userRepository.findByKakaoId(userInfo.getProviderId())
                .map(existing -> {
                    existing.updateProfile(userInfo.getNickname(), userInfo.getProfileImageUrl());
                    return existing;
                })
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .kakaoId(userInfo.getProviderId())
                                .email(userInfo.getEmail())
                                .nickname(userInfo.getNickname())
                                .profileImage(userInfo.getProfileImageUrl())
                                .role(Role.USER)
                                .build()
                ));
    }
}
