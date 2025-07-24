package leets.bookmark.domain.file.application.mapper;

import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public File toFile(FileSaveRequest request) {
        return File.builder()
                .user(User.builder().id(request.userId()).build())
                .bookmark(Bookmark.builder().id(request.bookmarkId()).build())
                .fileName(request.fileName())
                .fileUrl(request.fileUrl())
                .fileType(request.fileType())
                .build();
    }

    public FileResponse toFileResponse(File file){
        return FileResponse.builder()
                .fileUrl(file.getFileUrl())
                .fileName(file.getFileName())
                .build();
    }
}
