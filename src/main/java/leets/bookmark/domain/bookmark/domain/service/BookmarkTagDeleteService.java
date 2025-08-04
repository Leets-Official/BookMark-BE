package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkTagMappingRepository;
import leets.bookmark.domain.tag.domain.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkTagDeleteService {

    private final BookmarkTagMappingRepository bookmarkTagMappingRepository;

    public void deleteAllByBookmarks(List<Bookmark> bookmarks) {
        bookmarkTagMappingRepository.deleteByBookmarkIn(bookmarks);
    }

    public void deleteByTag(Tag tag) {
        bookmarkTagMappingRepository.deleteByTag(tag);
    }
}
