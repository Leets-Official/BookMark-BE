package leets.bookmark.domain.tag.domain.service;

import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.application.exception.TagNotFoundException;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.repository.TagRepository;
import leets.bookmark.domain.user.domain.entity.User;
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

    public List<Tag> findAllByUser(User user) {
        return tagRepository.findAllByCategory_User(user);
    }

    public long countByCategory(Category category) {
        return tagRepository.countByCategory(category);
    }

    public List<Tag> findAllByIds(List<Long> tagIds) {
        return tagRepository.findAllByIdIn(tagIds);
    }
}
