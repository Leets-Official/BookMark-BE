package leets.bookmark.global.auth.oauth2.application.usecase;

import leets.bookmark.domain.user.application.dto.response.UserKakaoLoginResponse;
import leets.bookmark.domain.user.application.mapper.UserMapper;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserSaveService;
import leets.bookmark.global.auth.jwt.application.dto.JwtTokenDto;
import leets.bookmark.global.auth.jwt.service.AesEncryptor;
import leets.bookmark.global.auth.jwt.service.JwtProvider;
import leets.bookmark.global.auth.oauth2.application.dto.response.KakaoTokenResponse;
import leets.bookmark.global.auth.oauth2.application.dto.response.KakaoUserInfoResponse;
import leets.bookmark.global.auth.oauth2.service.KakaoGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuth2UseCaseImpl implements OAuth2UseCase {

    private final UserSaveService userSaveService;
    private final KakaoGetService kakaoGetService;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;
    private final AesEncryptor aesEncryptor;

    @Transactional
    @Override
    public UserKakaoLoginResponse kakaoLogin(String code, String redirectUri) {
        KakaoTokenResponse kakaoToken = kakaoGetService.getTokenFromKakao(code, redirectUri);
        KakaoUserInfoResponse kakaoUserInfo = kakaoGetService.getUserInfo(kakaoToken.accessToken());

        User user = userSaveService.save(kakaoUserInfo);

        // Kakao 제공 토큰 저장
        String kakaoAccessToken = aesEncryptor.encrypt(kakaoToken.accessToken());
        String kakaoRefreshToken = aesEncryptor.encrypt(kakaoToken.refreshToken());
        user.updateKakaoTokens(kakaoAccessToken, kakaoRefreshToken);

        // JWT 토큰 발급 및 저장
        JwtTokenDto jwtToken = jwtProvider.createToken(user);
        user.updateJwtTokens(jwtToken.accessToken(), jwtToken.refreshToken());

        return userMapper.toUserKakaoLoginResponse(user);
    }
}
