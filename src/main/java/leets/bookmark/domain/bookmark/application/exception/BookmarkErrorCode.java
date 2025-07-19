package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements ErrorCode{
    BOOKMARK_NOT_FOUND_EXCEPTION(404, "해당 북마크를 찾을 수 없습니다.");

    private final int errorCode;
    private final String message;
}
