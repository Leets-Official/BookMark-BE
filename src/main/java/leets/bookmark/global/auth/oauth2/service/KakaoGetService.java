package leets.bookmark.global.auth.oauth2.service;

import leets.bookmark.global.auth.oauth2.application.dto.response.KakaoTokenResponse;
import leets.bookmark.global.auth.oauth2.application.dto.response.KakaoUserInfoResponse;
import leets.bookmark.global.auth.oauth2.application.exception.KakaoAccessTokenInvalidException;
import leets.bookmark.global.auth.oauth2.application.exception.KakaoAuthorizationCodeInvalidException;
import leets.bookmark.global.auth.oauth2.application.exception.KakaoServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoGetService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    public static final String BEARER = "Bearer ";
    public static final String APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8 = "application/x-www-form-urlencoded;charset=utf-8";

    public KakaoTokenResponse getTokenFromKakao(String code) {
        return WebClient.create(tokenUri).post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("code", code)
                        .build(true))
                .header(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(KakaoAuthorizationCodeInvalidException::new))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(KakaoServerException::new))
                .bodyToMono(KakaoTokenResponse.class)
                .block();
    }

    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        return WebClient.create(userInfoUri).get()
                .uri(uriBuilder -> uriBuilder
                        .build(true))
                .header(AUTHORIZATION, BEARER + accessToken) // access token 인가
                .header(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(KakaoAccessTokenInvalidException::new))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(KakaoServerException::new))
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();
    }
}
