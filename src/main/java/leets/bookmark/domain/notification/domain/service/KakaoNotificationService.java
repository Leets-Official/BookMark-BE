package leets.bookmark.domain.notification.domain.service;

import leets.bookmark.global.auth.oauth2.application.dto.request.NotificationItemRequest;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.oauth2.service.KakaoTokenRefreshService;
import leets.bookmark.global.auth.jwt.service.AesEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Service
public class KakaoNotificationService {

    @Value("${kakao.linked-url}")
    private String linkedUrl;

    @Value("${kakao.notification-title}")
    private String notificationTitle;

    @Value("${kakao.message-send-uri}")
    private String messageSendUri;

    private final RestClient kakaoRestClient;
    private final AesEncryptor aesEncryptor;

    private final KakaoTokenRefreshService kakaoTokenRefreshService;

    public void sendListTemplate(User user, List<NotificationItemRequest> notificationItemRequests) {

        kakaoTokenRefreshService.refreshAccessToken(user);  // 토큰 갱신
        
        StringBuilder contentsJson = new StringBuilder("[");

        for (int i = 0; i < notificationItemRequests.size(); i++) {
            NotificationItemRequest item = notificationItemRequests.get(i);
            contentsJson.append("""
                {
                    "title": "%s",
                    "description": "%s",
                    "image_url": "%s",
                    "image_width": 640,
                    "image_height": 640,
                    "link": {
                        "web_url": "%s",
                        "mobile_web_url": "%s"
                    }
                }
            """.formatted(
                    escapeJson(item.title()),
                    escapeJson(item.description()),
                    escapeJson(item.imageUrl()),
                    linkedUrl,
                    linkedUrl
            ));

            if (i < notificationItemRequests.size() - 1) {
                contentsJson.append(",");
            }
        }

        contentsJson.append("]");

        String templateJson = """
            {
                "object_type": "list",
                "header_title": "%s",
                "header_link": {
                    "web_url": "%s",
                    "mobile_web_url": "%s"
                },
                "contents": %s,
                "buttons": [
                    {
                        "title": "웹으로 이동",
                        "link": {
                            "web_url": "%s",
                            "mobile_web_url": "%s"
                        }
                    }
                ]
            }
        """.formatted(notificationTitle, linkedUrl, linkedUrl, contentsJson.toString(), linkedUrl, linkedUrl);

        String formBody = "template_object=" + URLEncoder.encode(templateJson, StandardCharsets.UTF_8);

        String response = kakaoRestClient.post()
                .uri(messageSendUri)
                .headers(headers -> {
                    headers.setBearerAuth(aesEncryptor.decrypt(user.getKakaoAccessToken()));
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                })
                .body(formBody)
                .retrieve()
                .body(String.class);
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"");
    }
}
