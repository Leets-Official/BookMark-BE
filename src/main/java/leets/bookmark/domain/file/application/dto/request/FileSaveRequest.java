package leets.bookmark.domain.file.application.dto.request;

import leets.bookmark.domain.file.domain.entity.enums.FileType;

public record FileSaveRequest(
        String fileName,
        String fileUrl,
        FileType fileType
) {}
