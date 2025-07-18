package leets.bookmark.domain.tag.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.domain.tag.application.dto.request.TagCreateRequest;
import leets.bookmark.domain.tag.application.usecase.TagUseCase;
import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static leets.bookmark.domain.tag.presentation.TagResponseMessage.*;

@Tag(name = "TAG", description = "태그 API")
@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagUseCase tagUseCase;

    @PostMapping
    @Operation(summary = "태그 생성 API", description = "태그를 생성할 수 있는 API입니다.")
    public CommonResponse<Void> createTag(
            @CurrentUser Long userId,
            @RequestBody @Validated TagCreateRequest request
    ) {
        tagUseCase.save(userId, request);
        return CommonResponse.createSuccess(CREATE_TAG_SUCCESS.getMessage());
    }

    @DeleteMapping("/{tagId}")
    @Operation(summary = "태그 삭제 API", description = "본인의 태그를 삭제할 수 있는 API입니다.")
    public CommonResponse<Void> deleteTag(
            @CurrentUser Long userId,
            @PathVariable Long tagId
    ) {
        tagUseCase.delete(userId, tagId);
        return CommonResponse.createSuccess(DELETE_TAG_SUCCESS.getMessage());
    }

}
