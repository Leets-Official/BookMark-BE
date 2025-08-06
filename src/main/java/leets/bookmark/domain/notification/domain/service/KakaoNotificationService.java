package leets.bookmark.domain.notification.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.*;

@RequiredArgsConstructor
@Service
public class KakaoNotificationService {

    @Value("${kakao.linked-url}")
    private String linkedUrl;

    @Value("${kakao.notification-title}")
    private String notificationTitle;

    @Value("${kakao.message-send-uri}")
    private String messageSendUri;

    @Value("${kakao.basic-image-url}")
    private String basicImageUrl;

    private final RestClient kakaoRestClient;
    private final AesEncryptor aesEncryptor;

    private final KakaoTokenRefreshService kakaoTokenRefreshService;

    public void sendListTemplate(User user, List<NotificationItemRequest> notificationItemRequests) throws JsonProcessingException {

        kakaoTokenRefreshService.refreshAccessToken(user);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> templateMap = new HashMap<>();

        // 알림이 1개일 경우
        if (notificationItemRequests.size() == 1) {
            NotificationItemRequest item = notificationItemRequests.get(0);

            templateMap.put("object_type", "feed");
            templateMap.put("content", Map.of(
                    "title", Optional.ofNullable(item.title()).orElse("제목 없음"),
                    "description", Optional.ofNullable(item.description()).orElse(" "),
                    "image_url", Optional.ofNullable(item.imageUrl()).orElse(basicImageUrl),
                    "image_width", 640,
                    "image_height", 640,
                    "link", Map.of(
                            "web_url", linkedUrl,
                            "mobile_web_url", linkedUrl
                    )
            ));

            templateMap.put("buttons", List.of(Map.of(
                    "title", "웹으로 이동",
                    "link", Map.of(
                            "web_url", linkedUrl,
                            "mobile_web_url", linkedUrl
                    )
            )));

        } else if (notificationItemRequests.size() >= 2) {
            templateMap.put("object_type", "list");
            templateMap.put("header_title", notificationTitle);
            templateMap.put("header_link", Map.of(
                    "web_url", linkedUrl,
                    "mobile_web_url", linkedUrl
            ));

            List<Map<String, Object>> contents = new ArrayList<>();
            for (NotificationItemRequest item : notificationItemRequests) {
                contents.add(Map.of(
                        "title", Optional.ofNullable(item.title()).orElse("제목 없음"),
                        "description", Optional.ofNullable(item.description()).orElse(" "),
                        "image_url", Optional.ofNullable(item.imageUrl()).orElse(basicImageUrl),
                        "image_width", 640,
                        "image_height", 640,
                        "link", Map.of(
                                "web_url", linkedUrl,
                                "mobile_web_url", linkedUrl
                        )
                ));
            }

            templateMap.put("contents", contents);
            templateMap.put("buttons", List.of(Map.of(
                    "title", "웹으로 이동",
                    "link", Map.of(
                            "web_url", linkedUrl,
                            "mobile_web_url", linkedUrl
                    )
            )));
        }

        String templateJson = objectMapper.writeValueAsString(templateMap);

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

}
