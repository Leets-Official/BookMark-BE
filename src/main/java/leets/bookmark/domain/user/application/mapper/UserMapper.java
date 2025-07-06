package leets.bookmark.domain.user.application.mapper;

import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

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
