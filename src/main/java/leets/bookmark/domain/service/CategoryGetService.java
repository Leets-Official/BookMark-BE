package leets.bookmark.domain.service;

import leets.bookmark.domain.entity.Category;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.repository.CategoryRepository;
import leets.bookmark.domain.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryGetService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public List<Category> getAllByUser(User user) {
        return categoryRepository.findAllByUser(user);
    }
}
