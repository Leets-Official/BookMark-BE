package leets.bookmark.domain.service;

import leets.bookmark.domain.entity.Category;
import leets.bookmark.domain.repository.CategoryRepository;
import leets.bookmark.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryReadService {

    private final CategoryRepository categoryRepository;

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 카테고리를 찾을 수 없습니다."));
    }

    public List<Category> getByUserId(Long userId) {
        return categoryRepository.findAllByUserId(userId);
    }
}
