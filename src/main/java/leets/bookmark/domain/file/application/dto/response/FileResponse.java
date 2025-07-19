package leets.bookmark.domain.file.application.dto.response;

import lombok.Builder;

@Builder
public record FileResponse(
        String fileName,
        String fileUrl
) {}
