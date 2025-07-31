package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkUpdateService {

    private final BookmarkTagMappingRepository tagMappingRepository;
    private final TagRepository tagRepository;

    @Transactional
    public void update(Bookmark bookmark, BookmarkUpdateRequest request) {
        FileSaveRequest fileRequest = request.file();
        bookmark.updateBookmark(
            request.title(),
            request.memo(),
            fileRequest != null ? fileRequest.fileUrl() : null,
            request.deviceType().name()
        );

        tagMappingRepository.deleteByBookmark(bookmark);

        List<Long> tagIds = request.tagIds();
        if (tagIds != null && !tagIds.isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(tagIds);
            for (Tag tag : tags) {
                bookmark.addTagMapping(BookmarkTagMapping.of(tag, bookmark));
            }
        }
    }
}
