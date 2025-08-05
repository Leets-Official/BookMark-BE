package leets.bookmark.domain.file.application.dto.response;

public record S3UrlResponse  (
        String originalFileName,
        String s3Url
) {}
