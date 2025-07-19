package leets.bookmark.global.auth.oauth2.userinfo;

import leets.bookmark.global.auth.oauth2.exception.OAuth2UserInfoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    public static final String KAKAO_ACCOUNT = "kakao_account";
    public static final String KAKAO_PROFILE = "profile";

    private final Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get(KAKAO_ACCOUNT);
        if (kakaoAccount == null || !kakaoAccount.containsKey("email")) {
            throw new OAuth2UserInfoException();
        }
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getNickname() {
        Map<String, Object> kakaoProfile = (Map<String, Object>) ((Map<String, Object>) attributes.get(KAKAO_ACCOUNT)).get(KAKAO_PROFILE);
        return (String) kakaoProfile.get("nickname");
    }

    @Override
    public String getProfileImageUrl() {
        Map<String, Object> kakaoProfile = (Map<String, Object>) ((Map<String, Object>) attributes.get(KAKAO_ACCOUNT)).get(KAKAO_PROFILE);
        return (String) kakaoProfile.get("profile_image_url");
    }
}
