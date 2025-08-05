package leets.bookmark.domain.file.application.mapper;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public File toFile(User user, Bookmark bookmark, FileSaveRequest fileSaveRequest, FileType fileType){
        return File.builder()
                .user(user)
                .bookmark(bookmark)
                .fileName(fileSaveRequest.fileName())
                .fileUrl(fileSaveRequest.fileUrl())
                .fileType(fileType)
                .build();
    }

    public FileResponse toFileResponse(File file){
        return FileResponse.builder()
                .fileId(file.getId())
                .fileUrl(file.getFileUrl())
                .fileName(file.getFileName())
                .createdAt(file.getCreatedAt())
                .updatedAt(file.getUpdatedAt())
                .build();
    }
}
