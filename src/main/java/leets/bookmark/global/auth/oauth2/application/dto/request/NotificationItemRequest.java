package leets.bookmark.global.auth.oauth2.application.dto.request;

import lombok.Builder;

@Builder
public record NotificationItemRequest(
        String title,
        String description,
        String imageUrl
) {}
