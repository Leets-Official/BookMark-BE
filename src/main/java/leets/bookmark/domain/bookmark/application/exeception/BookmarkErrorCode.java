package leets.bookmark.domain.bookmark.application.exeception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BookmarkErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않는 북마크입니다.");

    private final int status;
    private final String message;

    BookmarkErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
