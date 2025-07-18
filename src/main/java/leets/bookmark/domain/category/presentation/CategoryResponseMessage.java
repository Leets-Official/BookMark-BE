package leets.bookmark.domain.category.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryResponseMessage {

    CREATE_CATEGORY_SUCCESS("카테고리 생성에 성공했습니다.");

    private final String message;
}
