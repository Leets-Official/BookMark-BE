package leets.bookmark.domain.category.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryResponseMessage {

    GET_ALL_CATEGORIES_SUCCESS("전체 카테고리 조회에 성공했습니다."),
    CREATE_CATEGORY_SUCCESS("카테고리 생성에 성공했습니다."),
    UPDATE_CATEGORY_SUCCESS("카테고리 수정에 성공했습니다."),
    DELETE_CATEGORY_SUCCESS("카테고리 삭제에 성공했습니다.");

    private final String message;
}
