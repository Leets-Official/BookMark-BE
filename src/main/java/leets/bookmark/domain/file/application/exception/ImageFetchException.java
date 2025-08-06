package leets.bookmark.domain.file.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class ImageFetchException extends BusinessException {
    public ImageFetchException(){
        super(FileErrorCode.IMAGE_FETCH_EXCEPTION);
    }
}
