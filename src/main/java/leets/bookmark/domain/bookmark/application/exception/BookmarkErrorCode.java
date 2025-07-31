package leets.bookmark.domain.bookmark.application.exception;

import leets.bookmark.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements ErrorCode{
    BOOKMARK_NOT_FOUND_EXCEPTION(404, "해당 북마크를 찾을 수 없습니다."),
    CATEGORY_ID_REQUIRED(400, "북마크 필터링을 위해 categoryId는 필수입니다."),
    BOOKMARK_PREVIEW_FAILED(500, "북마크 미리보기 데이터 추출에 실패했습니다."),
    INVALID_PLATFORM_EXCEPTION(400, "지원하지 않는 플랫폼입니다");

    private final int errorCode;
    private final String message;
}
