package leets.bookmark.domain.file.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.domain.file.application.dto.response.PresignedUrlResponse;
import leets.bookmark.domain.file.application.dto.response.S3UrlResponse;
import leets.bookmark.domain.file.application.usecase.FileUseCase;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FILE", description = "파일 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileUseCase fileUseCase;

    @GetMapping("/presigned-url")
    public CommonResponse<PresignedUrlResponse> getPresignedUrl(@RequestParam String fileName) {
        PresignedUrlResponse response = fileUseCase.getPreSignedUrl(fileName);
        return CommonResponse.createSuccess(FileResponseCode.PRE_SIGNED_URL_SUCCESS.getMessage(), response);
    }

    @GetMapping("/upload")
    public CommonResponse<S3UrlResponse> getS3Url(@RequestParam String fileUrl) {
        S3UrlResponse response = fileUseCase.upload(fileUrl);
        return CommonResponse.createSuccess(FileResponseCode.S3_UPLOAD_SUCCESS.getMessage(), response);
    }
}
