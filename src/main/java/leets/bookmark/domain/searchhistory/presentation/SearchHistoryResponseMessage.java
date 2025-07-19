package leets.bookmark.domain.searchhistory.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchHistoryResponseMessage {

    GET_SUCCESS("검색 기록 조회에 성공했습니다."),
    DELETE_SUCCESS("검색 기록 삭제에 성공했습니다.");

    private final String message;


}