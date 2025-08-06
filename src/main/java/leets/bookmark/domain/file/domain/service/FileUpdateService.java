package leets.bookmark.domain.file.domain.service;

import leets.bookmark.domain.file.application.dto.request.FileUpdateRequest;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileUpdateService {

    public void update(File file, FileUpdateRequest fileUpdateRequest, FileType fileType) {
        file.updateFile(fileUpdateRequest.fileName(), fileUpdateRequest.fileUrl(), fileType);
    }

    public void updateThumbnailImage(File file, String fileName, String s3Url, FileType type) {
        file.updateFile(fileName, s3Url, type);
    }
}
