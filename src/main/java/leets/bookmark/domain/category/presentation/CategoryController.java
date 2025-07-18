package leets.bookmark.domain.category.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.domain.category.application.dto.request.CreateCategoryRequest;
import leets.bookmark.domain.category.application.dto.response.CategoryResponse;
import leets.bookmark.domain.category.application.usecase.CategoryUseCase;
import leets.bookmark.global.auth.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import leets.bookmark.global.common.response.CommonResponse;

import java.util.List;

import static leets.bookmark.domain.category.presentation.CategoryResponseMessage.*;

@Tag(name = "CATEGORY", description = "카테고리 관련 API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @GetMapping
    @Operation(summary = "전체 카테고리 조회 API", description = "사용자 본인의 전체 카테고리를 조회할 수 있는 API입니다.")
    public CommonResponse<List<CategoryResponse>> getAllCategories(@CurrentUser Long userId) {
        List<CategoryResponse> response = categoryUseCase.getAllCategories(userId);
        return CommonResponse.createSuccess(GET_ALL_CATEGORIES_SUCCESS.getMessage(), response);
    }

    @PostMapping
    @Operation(summary = "카테고리 생성 API", description = "카테고리를 생성할 수 있는 API입니다.")
    public CommonResponse<Void> createCategory(
            @CurrentUser Long userId,
            @Validated @RequestBody CreateCategoryRequest request
    ) {
        categoryUseCase.save(userId, request);
        return CommonResponse.createSuccess(CREATE_CATEGORY_SUCCESS.getMessage());
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "카테고리 삭제 API", description = "본인의 카테고리를 삭제할 수 있는 API입니다.")
    public CommonResponse<Void> deleteCategory(
            @CurrentUser Long userId,
            @PathVariable Long categoryId
    ) {
        categoryUseCase.delete(userId, categoryId);
        return CommonResponse.createSuccess(DELETE_CATEGORY_SUCCESS.getMessage());
    }
}
