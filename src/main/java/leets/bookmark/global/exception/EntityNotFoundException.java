package leets.bookmark.global.exception;

import leets.bookmark.global.common.entity.CategoryErrorCode;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final CategoryErrorCode errorCode;

    public EntityNotFoundException(CategoryErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
