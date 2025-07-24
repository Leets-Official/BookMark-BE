package leets.bookmark.domain.file.domain.service;

import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;

import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileSaveService {

    private final FileRepository fileRepository;

    public void save(File file){
        fileRepository.save(file);
    }

    public String upload(FileSaveRequest request) {
        File file = File.builder()
                .user(User.builder().id(request.userId()).build())
                .bookmark(Bookmark.builder().id(request.bookmarkId()).build())
                .fileName(request.fileName())
                .fileUrl(request.fileUrl())
                .fileType(request.fileType())
                .build();
        fileRepository.save(file);
        return file.getFileUrl();
    }
}
