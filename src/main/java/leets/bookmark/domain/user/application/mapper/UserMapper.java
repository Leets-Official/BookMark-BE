package leets.bookmark.domain.user.application.mapper;

import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.application.dto.response.UserKakaoLoginResponse;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.entity.enums.Role;
import leets.bookmark.domain.user.domain.entity.enums.Status;
import leets.bookmark.global.auth.oauth2.application.dto.response.KakaoUserInfoResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(KakaoUserInfoResponse userInfo) {
        return User.builder()
                .kakaoId(userInfo.id().toString())
                .email(userInfo.kakaoAccount().email())
                .nickname(userInfo.kakaoAccount().profile().nickName())
                .profileImage(userInfo.kakaoAccount().profile().profileImageUrl())
                .role(Role.USER)
                .status(Status.ACTIVE)
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

    public UserKakaoLoginResponse toUserKakaoLoginResponse(User user) {
        return UserKakaoLoginResponse.builder()
                .userId(user.getId())
                .profileImage(user.getProfileImage())
                .jwtAccessToken(user.getJwtAccessToken())
                .jwtRefreshToken(user.getJwtRefreshToken())
                .build();
    }
}
