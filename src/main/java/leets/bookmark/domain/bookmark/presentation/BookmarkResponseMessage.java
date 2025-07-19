package leets.bookmark.domain.bookmark.presentation;

import lombok.Getter;

@Getter
public enum BookmarkResponseMessage {
    BOOKMARK_SEARCH_SUCCESS("북마크 검색에 성공했습니다."),
    BOOKMARK_MEMO_SEARCH_SUCCESS("북마크 메모 검색에 성공했습니다."),
    BOOKMARK_FILTER_SUCCESS("북마크 필터링에 성공했습니다.");

    private final String message;

    BookmarkResponseMessage(String message) {
        this.message = message;
    }
}
