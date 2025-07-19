package leets.bookmark.global.common.response;

import leets.bookmark.global.common.response.ResponseMessage;
import lombok.Builder;

@Builder
public record ApiResponse<T>(
    String message,
    T data
) {
    public static <T> ApiResponse<T> success(ResponseMessage responseMessage, T data) {
        return ApiResponse.<T>builder()
            .message(responseMessage.getMessage())
            .data(data)
            .build();
    }
}
