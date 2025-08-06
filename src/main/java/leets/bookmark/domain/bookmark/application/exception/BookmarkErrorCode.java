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
    INVALID_PLATFORM_EXCEPTION(400, "지원하지 않는 플랫폼입니다."),
    TAG_CATEGORY_MISMATCH_EXCEPTION(400, "태그의 카테고리와 요청된 카테고리가 일치하지 않습니다."),
    NO_BOOKMARK_PERMISSION_EXCEPTION(403, "해당 북마크에 대한 권한이 없습니다."),
    INVALID_BOOKMARK_CATEGORY_EXCEPTION(400, "카테고리를 선택해야 합니다."),
    BOOKMARK_TAG_MINIMUM_REQUIRED_EXCEPTION(400, "최소 1개 이상의 태그를 선택해야 합니다."),
    BOOKMARK_TAG_COUNT_EXCEEDED_EXCEPTION(400, "태그는 최대 3개까지만 선택할 수 있습니다."),
    Bookmark_Update_Field_Empty_Exception(400, "업데이트할 필드가 존재하지 않습니다.");

    private final int errorCode;
    private final String message;
}
