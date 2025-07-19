package leets.bookmark.domain.file.domain.service;

import leets.bookmark.domain.file.application.exception.FileNotFoundException;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileGetService {

    private final FileRepository fileRepository;

    public File findByBookmarkId(long bookmarkId) {
        return fileRepository.findByBookmarkId(bookmarkId)
                .orElseThrow(FileNotFoundException::new);
    }
}
