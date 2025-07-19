package leets.bookmark.domain.notification.application.dto.request;

import lombok.Builder;

@Builder
public record NotificationItem(
        String title,
        String description,
        String imageUrl
) {}
