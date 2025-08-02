package leets.bookmark.domain.bookmark.presentation;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum BookmarkResponseMessage {
    BOOKMARK_SEARCH_SUCCESS("북마크 검색에 성공했습니다."),
    BOOKMARK_MEMO_SEARCH_SUCCESS("북마크 메모 검색에 성공했습니다."),
    BOOKMARK_FILTER_SUCCESS("북마크 필터링에 성공했습니다."),
    BOOKMARK_DELETE_SUCCESS("북마크 삭제에 성공했습니다."),
    BOOKMARK_UPDATE_SUCCESS("북마크 수정에 성공했습니다."),
    BOOKMARK_SAVE_SUCCESS("북마크 저장에 성공하였습니다."),
    BOOKMARK_PREVIEW_SUCCESS("썸네일, 제목, 파비콘, 플랫폼 추출에 성공했습니다.");

    private final String message;
}
