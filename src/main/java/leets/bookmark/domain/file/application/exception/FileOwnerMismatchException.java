package leets.bookmark.domain.file.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class FileOwnerMismatchException extends BusinessException {
    public FileOwnerMismatchException(){
        super(FileErrorCode.FILE_OWNER_MISMATCH_EXCEPTION);
    }
}
