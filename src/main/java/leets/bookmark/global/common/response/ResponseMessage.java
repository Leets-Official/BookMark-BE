package leets.bookmark.global.common.response;

public enum ResponseMessage {
    BOOKMARK_SEARCH_SUCCESS("북마크 검색에 성공했습니다."),
    BOOKMARK_MEMO_SEARCH_SUCCESS("북마크 메모 검색에 성공했습니다.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
