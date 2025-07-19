package leets.bookmark.domain.service;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.entity.Bookmark;
import leets.bookmark.application.mapper.BookmarkMapper;
import leets.bookmark.domain.repository.BookmarkRepository;
import leets.bookmark.global.common.response.CommonResponse;
import leets.bookmark.global.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkGetService {

    private final BookmarkRepository bookmarkRepository;

    public List<Bookmark> getBookmarksAllByMemo(String keyword) {
        return bookmarkRepository.findByMemoContaining(keyword);
    }
}
