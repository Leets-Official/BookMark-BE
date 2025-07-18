package leets.bookmark.domain.tag.domain.service;

import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.application.exception.TagNotFoundException;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagGetService {

    private final TagRepository tagRepository;

    public Tag findById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);
    }

    public boolean existsByCategoryAndTagName(Category category, String tagName) {
        return tagRepository.existsByCategoryAndTagName(category, tagName);
    }

    public List<Tag> findAllByCategory(Category category) {
        return tagRepository.findAllByCategory(category);
    }
}
