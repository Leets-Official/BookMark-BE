package leets.bookmark.domain.tag.domain.service;

import leets.bookmark.domain.bookmark.domain.service.BookmarkTagDeleteService;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagDeleteService {

    private final TagRepository tagRepository;
    private final BookmarkTagDeleteService bookmarkTagDeleteService;

    public void delete(Tag tag) {
        bookmarkTagDeleteService.deleteByTag(tag);
        tagRepository.delete(tag);
    }

    public void deleteAllByCategory(Category category) {
        tagRepository.deleteAllByCategory(category);
    }
}
