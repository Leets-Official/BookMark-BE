package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkUpdateRequest;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkUpdateService {

    public void update(
            Bookmark bookmark,
            BookmarkUpdateRequest request
    ) {
        FileSaveRequest fileRequest = request.file();
        bookmark.updateBookmark(
                request.title(),
                request.memo(),
                fileRequest != null ? fileRequest.fileUrl() : null,
                fileRequest != null ? fileRequest.fileType() : null,
                request.platform()
        );

    }
}
