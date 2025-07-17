package leets.bookmark.global.auth.oauth2.usecase;

import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.oauth2.userinfo.KakaoOAuth2UserInfo;

public interface UserOAuth2UseCase {

    User oAuth2Login(KakaoOAuth2UserInfo userInfo);
}
