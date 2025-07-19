package leets.bookmark.domain.notification.application.dto.request;

public record ContentItem(
        String title,
        String description,
        String imageUrl
) {}
