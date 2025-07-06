package leets.bookmark.domain.user.domain.entity;

import jakarta.persistence.*;
import leets.bookmark.domain.user.domain.entity.enums.Role;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@AllArgsConstructor
@SuperBuilder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kakaoId;

    private String email;

    private String nickname;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String accessToken;

    private String refreshToken;

    public void updateProfile(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void updateTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
