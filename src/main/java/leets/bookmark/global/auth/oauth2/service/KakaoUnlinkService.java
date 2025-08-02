package leets.bookmark.global.auth.oauth2.service;

import leets.bookmark.global.auth.oauth2.application.exception.KakaoAccessTokenInvalidException;
import leets.bookmark.global.auth.oauth2.application.exception.KakaoServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Service
@RequiredArgsConstructor
public class KakaoUnlinkService {

    public static final String KAKAO_UNLINK_URI = "https://kapi.kakao.com/v1/user/unlink";
    public static final String BEARER = "Bearer ";
    public static final String APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8 = "application/x-www-form-urlencoded;charset=utf-8";

    public void unlink(String kakaoAccessToken) {
        WebClient.create(KAKAO_UNLINK_URI).post()
                .uri(uriBuilder -> uriBuilder
                        .build(true))
                .header(AUTHORIZATION, BEARER + kakaoAccessToken)
                .header(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(KakaoAccessTokenInvalidException::new))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(KakaoServerException::new))
                .bodyToMono(String.class)
                .block();
    }
}
