package leets.bookmark.global.common.exception;

import lombok.Builder;

@Builder
public record BindExceptionResponse(
        String message,
        Object data
) {}
