package leets.bookmark.global.auth.jwt.application.exception;

import leets.bookmark.global.common.exception.BusinessException;

public class AesDecryptionException extends BusinessException {
    public AesDecryptionException(){
        super(AuthJwtErrorCode.AES_DECRYPT_EXCEPTION);
    }
}
