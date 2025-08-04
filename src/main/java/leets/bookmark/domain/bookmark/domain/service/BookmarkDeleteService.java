package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.file.domain.service.FileDeleteService;
import leets.bookmark.domain.notification.domain.service.NotificationDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkDeleteService {

    private final BookmarkRepository bookmarkRepository;
    private final FileDeleteService fileDeleteService;
    private final NotificationDeleteService notificationDeleteService;
    private final BookmarkTagDeleteService bookmarkTagDeleteService;

    public void delete(final Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }

    public void deleteAllBookmarks(List<Bookmark> bookmarks) {
        bookmarks.forEach(Bookmark::deleteFile);
        fileDeleteService.deleteByBookmarks(bookmarks);
        bookmarkTagDeleteService.deleteAllByBookmarks(bookmarks);
        notificationDeleteService.deleteByBookmarks(bookmarks);
        bookmarkRepository.deleteAll(bookmarks);
    }
}
