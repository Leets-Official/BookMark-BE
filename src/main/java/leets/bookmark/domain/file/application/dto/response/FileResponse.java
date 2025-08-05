package leets.bookmark.domain.file.application.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FileResponse(
        Long fileId,
        String fileName,
        String fileUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
