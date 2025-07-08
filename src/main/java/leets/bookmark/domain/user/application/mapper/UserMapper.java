package leets.bookmark.domain.user.application.mapper;

import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.entity.enums.Role;
import leets.bookmark.global.auth.oauth2.userinfo.KakaoOAuth2UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUserEntity(KakaoOAuth2UserInfo userInfo) {
        return User.builder()
                .kakaoId(userInfo.getProviderId())
                .email(userInfo.getEmail())
                .nickname(userInfo.getNickname())
                .profileImage(userInfo.getProfileImageUrl())
                .role(Role.USER)
                .build();
    }

    public UserInfoResponse toUserInfoResponse(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .kakaoId(user.getKakaoId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .role(String.valueOf(user.getRole()))
                .build();
    }
}
