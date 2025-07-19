package leets.bookmark.domain.file.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class InvalidFileExtensionException extends BusinessException {
    public InvalidFileExtensionException(){
        super(FileErrorCode.INVALID_FILE_EXTENSION);
    }
}
