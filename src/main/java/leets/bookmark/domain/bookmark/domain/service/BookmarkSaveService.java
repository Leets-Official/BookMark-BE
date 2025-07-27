package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSaveRequest;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.tag.domain.repository.TagRepository;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import leets.bookmark.domain.tag.domain.entity.Tag;
import java.util.List;
import java.util.stream.Collectors;
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

    public void save(BookmarkSaveRequest request, Long userId) {
        Bookmark bookmark = bookmarkMapper.toBookmark(userId,request);
        bookmarkRepository.save(bookmark);
        updateCategoryAndTags(bookmark, request.categoryId(), request.tagIds());
    }

    public void updateCategoryAndTags(Bookmark bookmark, Long categoryId, List<Long> tagIds) {
        List<BookmarkTagMapping> mappings = tagIds.stream()
            .map(tagId -> {
                Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("Tag not found: " + tagId));
                return BookmarkTagMapping.builder()
                    .bookmark(bookmark)
                    .tag(tag)
                    .build();
            })
            .collect(Collectors.toList());

        bookmarkTagMappingRepository.saveAll(mappings);
    }
}
