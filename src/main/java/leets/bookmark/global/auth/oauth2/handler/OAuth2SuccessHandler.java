package leets.bookmark.global.auth.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.repository.UserRepository;
import leets.bookmark.global.auth.jwt.application.dto.JwtTokenDto;
import leets.bookmark.global.auth.jwt.service.JwtProvider;
import leets.bookmark.global.auth.oauth2.application.exception.KakaoUserNotFoundException;
import leets.bookmark.global.auth.oauth2.userinfo.KakaoOAuth2UserInfo;
import leets.bookmark.global.auth.jwt.service.AesEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final AesEncryptor aesEncryptor;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        KakaoOAuth2UserInfo userInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());

        String kakaoId = userInfo.getProviderId();
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseThrow(KakaoUserNotFoundException::new);

        // Kakao 제공 토큰 추출 및 저장
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authToken.getAuthorizedClientRegistrationId(),
                authToken.getName()
        );

        String accessToken = aesEncryptor.encrypt(client.getAccessToken().getTokenValue());

        if (client.getRefreshToken() != null) {
            String refreshToken = aesEncryptor.encrypt(client.getRefreshToken().getTokenValue());
            user.updateKakaoTokens(accessToken, refreshToken);
        } else {
            user.updateKakaoAccessToken(accessToken);
        }

        // JWT 토큰 발급 및 저장
        JwtTokenDto token = jwtProvider.createToken(user);
        user.updateTokens(token.accessToken(), token.refreshToken());

        userRepository.save(user);

        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), Map.of(
                "id", user.getId(),
                "nickname", user.getNickname(),
                "email", user.getEmail(),
                "profileImage", user.getProfileImage(),
                "accessToken", token.accessToken(),
                "refreshToken", token.refreshToken()
        ));
    }
}
