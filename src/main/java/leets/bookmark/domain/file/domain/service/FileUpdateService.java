package leets.bookmark.domain.file.domain.service;

import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.file.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileUpdateService {
    private final FileRepository fileRepository;

    public void update(File file, String fileName, String fileUrl, FileType fileType) {
        file.updateFile(fileName, fileUrl, fileType);
    }
}
