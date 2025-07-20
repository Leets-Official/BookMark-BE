package leets.bookmark.global.auth.oauth2.service;

import leets.bookmark.domain.notification.application.dto.response.KakaoTokenResponse;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.oauth2.application.exception.KakaoTokenRefreshException;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoTokenRefreshService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void refreshAccessToken(User user) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", clientId);
        params.add("refresh_token", user.getKakaoRefreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    request,
                    KakaoTokenResponse.class
            );
            user.updateKakaoAccessToken(response.getBody().accessToken());

        } catch (Exception e) {
            throw new KakaoTokenRefreshException();
        }
    }
}
