package leets.bookmark.global.auth.oauth2.application.usecase;

import leets.bookmark.domain.user.application.dto.response.UserKakaoLoginResponse;

public interface UserOAuth2UseCase {

    UserKakaoLoginResponse kakaoLogin(String code);
}
