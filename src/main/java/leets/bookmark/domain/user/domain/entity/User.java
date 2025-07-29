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

    private String jwtAccessToken;

    private String jwtRefreshToken;

    private String kakaoAccessToken;

    private String kakaoRefreshToken;

    public void updateJwtTokens(String jwtAccessToken, String jwtRefreshToken) {
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }

    public void updateJwtAccessToken(String jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateKakaoTokens(String kakaoAccessToken, String kakaoRefreshToken) {
        this.kakaoAccessToken = kakaoAccessToken;
        this.kakaoRefreshToken = kakaoRefreshToken;
    }

    public void updateKakaoAccessToken(String kakaoAccessToken) {
        this.kakaoAccessToken = kakaoAccessToken;
    }

    public void deleteAllTokens() {
        this.jwtAccessToken = null;
        this.jwtRefreshToken = null;
        this.kakaoAccessToken = null;
        this.kakaoRefreshToken = null;
    }
}
