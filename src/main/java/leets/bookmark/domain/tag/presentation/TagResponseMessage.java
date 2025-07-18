package leets.bookmark.domain.tag.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagResponseMessage {

    FIND_ALL_TAGS_SUCCESS("태그 조회에 성공했습니다."),
    CREATE_TAG_SUCCESS("태그 생성에 성공했습니다."),
    DELETE_TAG_SUCCESS("태그 삭제에 성공했습니다.");

    private final String message;
}
