package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.tag.domain.repository.TagRepository;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.application.exception.TagNotFoundException;
import java.util.List;

import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkSaveService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkMapper bookmarkMapper;
    private final TagRepository tagRepository;
    private final BookmarkTagMappingRepository bookmarkTagMappingRepository;

    public void save(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    public Bookmark save(BookmarkSaveRequest request, User user) {
        Bookmark bookmark = bookmarkMapper.toBookmark(user, request);
        Bookmark saved = bookmarkRepository.save(bookmark);

        for (Long tagId : request.tagIds()) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(TagNotFoundException::new);
            BookmarkTagMapping mapping = BookmarkTagMapping.of(tag, saved);
            bookmarkTagMappingRepository.save(mapping);
        }

        return saved;
    }

    public void updateCategoryAndTags(Bookmark bookmark, Long categoryId, List<Long> tagIds) {
        bookmark.clearTagMappings();

        for (Long tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);
            BookmarkTagMapping mapping = BookmarkTagMapping.builder()
                .tag(tag)
                .build();
            bookmark.addTagMapping(mapping);
        }
    }
}
