package leets.bookmark.domain.notification.domain.service;

import leets.bookmark.domain.notification.application.dto.request.NotificationItem;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.oauth2.service.KakaoTokenRefreshService;
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

    @Value("${kakao.base-url}")
    private String baseUrl;

    @Value("${kakao.notification-title}")
    private String notificationTitle;

    private final RestClient kakaoRestClient;

    private final KakaoTokenRefreshService kakaoTokenRefreshService;

    public void sendListTemplate(User user, List<NotificationItem> notificationItems) {

        kakaoTokenRefreshService.refreshAccessToken(user);  // 토큰 갱신
        
        StringBuilder contentsJson = new StringBuilder("[");

        for (int i = 0; i < notificationItems.size(); i++) {
            NotificationItem item = notificationItems.get(i);
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
                    baseUrl,
                    baseUrl
            ));

            if (i < notificationItems.size() - 1) {
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
        """.formatted(notificationTitle, baseUrl, baseUrl, contentsJson.toString(), baseUrl, baseUrl);

        String formBody = "template_object=" + URLEncoder.encode(templateJson, StandardCharsets.UTF_8);

        String response = kakaoRestClient.post()
                .uri("/v2/api/talk/memo/default/send")
                .headers(headers -> {
                    headers.setBearerAuth(user.getKakaoAccessToken());
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
