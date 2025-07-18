package leets.bookmark.domain.category.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.domain.category.application.dto.request.CreateCategoryRequest;
import leets.bookmark.domain.category.application.usecase.CategoryUseCase;
import leets.bookmark.global.auth.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import leets.bookmark.global.common.response.CommonResponse;

import static leets.bookmark.domain.category.presentation.CategoryResponseMessage.*;

@Tag(name = "CATEGORY", description = "카테고리 관련 API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @PostMapping
    public CommonResponse<Void> createCategory(
            @CurrentUser Long userId,
            @Validated @RequestBody CreateCategoryRequest request
    ) {
        categoryUseCase.save(userId, request);
        return CommonResponse.createSuccess(CREATE_CATEGORY_SUCCESS.getMessage());
    }
}
