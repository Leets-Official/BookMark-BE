package leets.bookmark.domain.bookmark.application.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

import leets.bookmark.domain.file.application.dto.response.FileResponse;

@Builder
public record BookmarkResponse(
        Long id,
        String url,
        String title,
        String memo,
        String platform,
        String faviconUrl,
        List<BookmarkTagInfoResponse> categoryTagInfos,
        FileResponse file,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
