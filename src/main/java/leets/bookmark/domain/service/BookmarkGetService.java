package leets.bookmark.domain.service;

import leets.bookmark.domain.entity.Bookmark;

import java.util.List;

public interface BookmarkGetService {
    List<Bookmark> getBookmarksByMemo(String keyword);
}
