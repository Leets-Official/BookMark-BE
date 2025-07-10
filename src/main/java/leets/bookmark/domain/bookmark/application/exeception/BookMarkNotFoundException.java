package leets.bookmark.domain.bookmark.application.exeception;

import leets.bookmark.global.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class BookMarkNotFoundException extends BusinessException {
    public BookMarkNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "존재하지 않는 북마크입니다.");
    }
}
