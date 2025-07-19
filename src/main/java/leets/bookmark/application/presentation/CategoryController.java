package leets.bookmark.application.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.usecase.CategoryUseCase;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import leets.bookmark.global.common.response.CommonResponse;

@Tag(name = "Categories", description = "카테고리 관련 API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase createCategoryUseCase;

    @PostMapping
    public CommonResponse<Void> createCategory(
            @CurrentUser Long userId,
            @Validated @RequestBody CreateCategoryRequest request
    ) {
        User user = User.builder().id(userId).build();
        createCategoryUseCase.save(user, request);
        return CommonResponse.createSuccess("카테고리 생성 성공");
    }
}
