package leets.bookmark.domain.file.domain.service;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FileDeleteService {

    private final FileRepository fileRepository;

    public void delete(File file) {
        fileRepository.delete(file);
    }

    public void deleteByBookmarks(List<Bookmark> bookmarks) {
        fileRepository.deleteByBookmarkIn(bookmarks);
    }
}
