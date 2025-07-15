package leets.bookmark.domain.bookmark.application.exeception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements ErrorCode {
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "존재하지 않는 북마크입니다.");

    private final HttpStatus status;
    private final int errorCode;
    private final String message;

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}
