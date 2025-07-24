package leets.bookmark.domain.file.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import software.amazon.awssdk.annotations.NotNull;

public record FileSaveRequest(
        Long userId,
        Long bookmarkId,
        @NotBlank
        String fileName,
        @NotBlank
        String fileUrl,
        @NotNull
        FileType fileType
) {}
