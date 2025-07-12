package leets.bookmark.domain.file.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class FileNotFoundException extends BusinessException {
    public FileNotFoundException(){
        super(FileErrorCode.FILE_NOT_FOUND_EXCEPTION);
    }
}
