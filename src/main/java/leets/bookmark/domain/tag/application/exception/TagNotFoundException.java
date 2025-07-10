package leets.bookmark.domain.tag.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class TagNotFoundException extends BusinessException {
    public TagNotFoundException() {
        super(404, "해당 태그가 존재하지 않습니다.");
    }
}
