package leets.bookmark.global.common.response;

import leets.bookmark.domain.bookmark.presentation.BookmarkResponseMessage;
import lombok.Builder;

@Builder
public record ApiResponse<T>(
    String message,
    T data
) {
    public static <T> ApiResponse<T> success(BookmarkResponseMessage responseMessage, T data) {
        return ApiResponse.<T>builder()
            .message(responseMessage.getMessage())
            .data(data)
            .build();
    }
}
