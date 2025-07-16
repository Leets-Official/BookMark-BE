package leets.bookmark.application.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.bookmark.application.dto.request.CreateCategoryRequest;
import leets.bookmark.application.usecase.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "카테고리 관련 API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase createCategoryUseCase;

    @PostMapping
    public ResponseEntity<Void> createCategory(
            @RequestBody @Valid CreateCategoryRequest request,
            @RequestHeader("X-USER-ID") Long userId
    ) {
        createCategoryUseCase.save(userId, request);
        return ResponseEntity.ok().build();
    }
}
