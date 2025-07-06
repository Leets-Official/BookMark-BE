package leets.bookmark.global.auth.oauth2.service;

import jakarta.transaction.Transactional;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.entity.enums.Role;
import leets.bookmark.domain.user.domain.repository.UserRepository;
import leets.bookmark.global.auth.oauth2.userinfo.KakaoOAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        KakaoOAuth2UserInfo userInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());

        String kakaoId = userInfo.getProviderId();
        String email = userInfo.getEmail();
        String nickname = userInfo.getNickname();
        String profileImage = userInfo.getProfileImageUrl();

        User user = userRepository.findByKakaoId(kakaoId)
                .map(existing -> {
                    existing.updateProfile(nickname, profileImage);
                    return existing;
                })
                .orElseGet(() -> {
                    // 신규 가입
                    return userRepository.save(
                            User.builder()
                                    .kakaoId(kakaoId)
                                    .email(email)
                                    .nickname(nickname)
                                    .profileImage(profileImage)
                                    .role(Role.USER)
                                    .build()
                    );
                });

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "id");
    }
}