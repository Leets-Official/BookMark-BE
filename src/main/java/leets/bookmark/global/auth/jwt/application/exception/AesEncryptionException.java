package leets.bookmark.global.auth.jwt.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class AesEncryptionException extends BusinessException {
    public AesEncryptionException(){
        super(AuthJwtErrorCode.AES_ENCRYPT_EXCEPTION);
    }
}
