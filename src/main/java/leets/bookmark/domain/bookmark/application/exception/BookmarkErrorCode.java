package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements ErrorCode{
    BOOKMARK_NOT_FOUND_EXCEPTION(404, "해당 북마크를 찾을 수 없습니다."),
    CATEGORY_ID_REQUIRED(400, "북마크 필터링을 위해 categoryId는 필수입니다."),
    NO_BOOKMARK_PERMISSION_EXCEPTION(403, "해당 북마크에 대한 권한이 없습니다.");

    private final int errorCode;
    private final String message;
}
