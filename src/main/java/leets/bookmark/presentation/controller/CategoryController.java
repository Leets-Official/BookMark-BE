package leets.bookmark.presentation.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<Void> createCategory(
            @RequestBody @Valid CreateCategoryRequest request,
            @RequestHeader("X-USER-ID") Long userId
    ) {
        createCategoryUseCase.save(userId, request);
        return ResponseEntity.ok().build();
    }
}
