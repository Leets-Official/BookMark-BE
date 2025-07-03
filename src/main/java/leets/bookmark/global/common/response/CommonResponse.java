package leets.bookmark.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> CommonResponse<T> createSuccess(String message){
        return new CommonResponse<>(200, message, null);
    }

    public static <T> CommonResponse<T> createSuccess(String message, T data){
        return new CommonResponse<>(200, message, data);
    }

    public static <T> CommonResponse<T> createFailure(int statusCode, String message){
        return new CommonResponse<>(statusCode, message, null);
    }

    public static <T> CommonResponse<T> createFailure(int statusCode, String message, T data){
        return new CommonResponse<>(statusCode, message, data);
    }
}
