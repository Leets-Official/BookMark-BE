package leets.bookmark.presentation;

import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.dto.response.CategoryResponse;
import leets.bookmark.application.usecase.CreateCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody CreateCategoryRequest request,
            @RequestHeader("X-USER-ID") Long userId // 실제 프로젝트에 맞게 수정 가능
    ) {
        CategoryResponse response = createCategoryUseCase.createCategory(userId, request);
        return ResponseEntity.ok(response);
    }
}
