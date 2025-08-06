package leets.bookmark.domain.file.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class S3UploadException extends BusinessException {
    public S3UploadException(){
        super(FileErrorCode.S3_UPLOAD_EXCEPTION);
    }
}
