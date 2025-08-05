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

    public File toThumbnailFile(User user, Bookmark bookmark, String fileName,
                                String s3UrlResponse, FileType type) {
        return File.builder()
                .user(user)
                .bookmark(bookmark)
                .fileName(fileName)
                .fileUrl(s3UrlResponse)
                .fileType(type)
                .build();
    }

    public FileResponse toFileResponse(File file){
        return FileResponse.builder()
                .fileUrl(file.getFileUrl())
                .fileName(file.getFileName())
                .build();
    }
}
