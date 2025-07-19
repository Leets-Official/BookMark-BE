package leets.bookmark.domain.searchhistory.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchHistoryResponseMessage {

    GET_SUCCESS("검색 기록 조회 성공"),
    DELETE_SUCCESS("검색 기록 삭제 성공");

    private final String message;


}