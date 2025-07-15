package leets.bookmark.global.common.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    // 비지니스 예외 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Void>> handle(BusinessException ex) {

        CommonResponse<Void> response = CommonResponse.createFailure(ex.getStatusCode(), ex.getMessage());

        log.warn("BusinessException: ", ex);
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(), ex.getStatusCode(), ex.getMessage());

        return ResponseEntity
            .status(ex.getStatusCode())
            .body(response);
    }

    //	@ModelAttribute로 받은 값의 유효성 검사 또는 타입 변환 실패
    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<List<BindExceptionResponse>>> handle(BindException ex) {
        List<BindExceptionResponse> exceptionResponses = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            exceptionResponses.add(BindExceptionResponse.builder()
                .message(fieldError.getDefaultMessage())
                .data(fieldError.getRejectedValue())
                .build());
        });

        CommonResponse<List<BindExceptionResponse>> response = CommonResponse.createFailure(
                GlobalErrorCode.BIND_EXCEPTION.getStatusCode(),
                GlobalErrorCode.BIND_EXCEPTION.getMessage(),
                exceptionResponses);

        log.warn("BindException: ", ex);
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(), GlobalErrorCode.BIND_EXCEPTION.getStatusCode(), exceptionResponses);

        return ResponseEntity
                .status(GlobalErrorCode.BIND_EXCEPTION.getStatusCode())
                .body(response);
    }

    // 요청 파라미터(RequestParam, @PathVariable)의 타입 불일치
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse<Void>> handle(MethodArgumentTypeMismatchException ex) {

        CommonResponse<Void> response = CommonResponse.createFailure(GlobalErrorCode.ARGUMENT_TYPE_MISMATCH_EXCEPTION.getStatusCode(),
                GlobalErrorCode.ARGUMENT_TYPE_MISMATCH_EXCEPTION.getFormatted(ex.getName()));

        log.warn("MethodArgumentTypeMismatchException");
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(), GlobalErrorCode.ARGUMENT_TYPE_MISMATCH_EXCEPTION.getStatusCode(), ex.getMessage());

        return ResponseEntity
                .status(GlobalErrorCode.ARGUMENT_TYPE_MISMATCH_EXCEPTION.getStatusCode())
                .body(response);
    }

    // @RequestBody로 받은 값의 유효성 검사 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<List<ArgumentNotValidExceptionResponse>>> handleValidation(MethodArgumentNotValidException ex) {

        List<ArgumentNotValidExceptionResponse> exceptionResponses = ex.getBindingResult()
            .getFieldErrors().stream()
            .map(error -> ArgumentNotValidExceptionResponse.builder()
                .field(error.getField())
                .message(error.getDefaultMessage())
                .data(error.getRejectedValue())
                .build()
            ).toList();

        CommonResponse<List<ArgumentNotValidExceptionResponse>> response = CommonResponse.createFailure(
                GlobalErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.getStatusCode(),
                GlobalErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.getMessage(),
                exceptionResponses);

        log.warn("MethodArgumentNotValidException: ", ex);
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(), GlobalErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.getStatusCode(), exceptionResponses);

        return ResponseEntity
                .status(GlobalErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.getStatusCode())
                .body(response);
    }

    // @RequestBody로 전달된 JSON이 잘못되었거나, 필드 타입이 일치하지 않는 경우
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse<Void>> handle(HttpMessageNotReadableException ex){
        // DTO 필드 타입 불일치
        if (ex.getCause() instanceof MismatchedInputException mismatched) {
            String fieldName = null;

            // 오류 발생 경로 중 가장 마지막 필드가 실제 문제 필드
            for (Reference ref : mismatched.getPath()) {
                fieldName = ref.getFieldName();
            }

            log.warn("MethodArgumentTypeMismatchException");
            log.warn(LOG_FORMAT, ex.getClass().getSimpleName(), GlobalErrorCode.MISMATCHED_INPUT_EXCEPTION.getStatusCode(), ex.getMessage());

            CommonResponse<Void> response = CommonResponse.createFailure(
                    GlobalErrorCode.MISMATCHED_INPUT_EXCEPTION.getStatusCode(),
                    GlobalErrorCode.MISMATCHED_INPUT_EXCEPTION.getFormatted(fieldName));
            return ResponseEntity
                    .status(GlobalErrorCode.MISMATCHED_INPUT_EXCEPTION.getStatus())
                    .body(response);
        }
        // 이외의 경우
        log.warn("HttpMessageNotReadableException");
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(), GlobalErrorCode.JSON_PARSE_EXCEPTION.getStatusCode(), ex.getMessage());

        CommonResponse<Void> response = CommonResponse.createFailure(GlobalErrorCode.JSON_PARSE_EXCEPTION.getStatusCode(), GlobalErrorCode.JSON_PARSE_EXCEPTION.getMessage());

        return ResponseEntity
                .status(GlobalErrorCode.JSON_PARSE_EXCEPTION.getStatus())
                .body(response);
    }

    // 이외 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handle(Exception ex) {
        int statusCode = 500;

        if (ex instanceof ErrorResponse) {
            statusCode = ((ErrorResponse) ex).getStatusCode().value();
        }

        log.warn("기타 Exception: ", ex);
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(), statusCode, ex.getMessage());

        CommonResponse<Void> response = CommonResponse.createFailure(statusCode, ex.getMessage());

        return ResponseEntity
            .status(statusCode)
            .body(response);
    }

}