package leets.bookmark.domain.file.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.domain.file.application.dto.response.PresignedUrlResponse;
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

    @GetMapping
    public CommonResponse<PresignedUrlResponse> getPresignedUrl(@RequestParam String fileName){
        PresignedUrlResponse response = fileUseCase.getPreSignedUrl(fileName);
        return CommonResponse.createSuccess(FileResponseCode.PRE_SIGNED_URL_SUCCESS.getMessage(), response);
    }
}
