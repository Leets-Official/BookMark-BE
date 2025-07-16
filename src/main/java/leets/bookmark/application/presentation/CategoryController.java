package leets.bookmark.application.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.usecase.CategoryUseCase;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import leets.bookmark.global.common.response.CommonResponse;

@Tag(name = "Category", description = "카테고리 관련 API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase createCategoryUseCase;

    @PostMapping
    public ResponseEntity<CommonResponse<Void>> createCategory(
            @RequestBody @Valid CreateCategoryRequest request,
            @AuthenticationPrincipal User user
    ) {
        createCategoryUseCase.save(user.getId(), request);
        return ResponseEntity.ok(CommonResponse.createSuccess("카테고리 생성 성공"));
    }
}
