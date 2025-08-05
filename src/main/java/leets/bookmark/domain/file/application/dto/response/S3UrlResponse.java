package leets.bookmark.domain.file.application.dto.response;

import lombok.Builder;

@Builder
public record S3UrlResponse  (
        String originalFileName,
        String s3Url
) {}
