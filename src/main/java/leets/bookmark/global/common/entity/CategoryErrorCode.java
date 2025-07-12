package leets.bookmark.global.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode {
    DUPLICATE_NAME("이미 존재하는 카테고리 이름입니다.");

    private final String message;
}