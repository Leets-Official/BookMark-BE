package leets.bookmark.global.common.response;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
    String message,
    T data
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
            .message(message)
            .data(data)
            .build();
    }
}
