package leets.bookmark.global.auth.oauth2.userinfo;

public interface OAuth2UserInfo {
    String getProviderId();
    String getEmail();
    String getNickname();
    String getProfileImageUrl();
}
