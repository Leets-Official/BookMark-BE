package leets.bookmark.global.auth.oauth2.userinfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccout = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccout.get("email");
    }

    @Override
    public String getNickname() {
        Map<String, Object> kakaoProfile = (Map<String, Object>) ((Map<String, Object>) attributes.get("kakao_account")).get("profile");
        return (String) kakaoProfile.get("nickname");
    }

    @Override
    public String getProfileImageUrl() {
        Map<String, Object> kakaoProfile = (Map<String, Object>) ((Map<String, Object>) attributes.get("kakao_account")).get("profile");
        return (String) kakaoProfile.get("profile_image_url");
    }
}
