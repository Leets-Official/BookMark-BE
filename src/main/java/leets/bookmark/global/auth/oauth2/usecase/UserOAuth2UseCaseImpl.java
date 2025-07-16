package leets.bookmark.global.auth.oauth2.usecase;

import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserSaveService;
import leets.bookmark.global.auth.oauth2.userinfo.KakaoOAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserOAuth2UseCaseImpl implements UserOAuth2UseCase {

    private final UserSaveService userSaveService;

    @Transactional
    @Override
    public User oAuth2Login(KakaoOAuth2UserInfo userInfo) {
        return userSaveService.save(userInfo);
    }
}
