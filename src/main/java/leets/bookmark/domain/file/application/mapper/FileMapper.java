package leets.bookmark.domain.file.application.mapper;

import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public File toFile(User user, Long bookmarkId, FileSaveRequest fileSaveRequest){
        return File.builder()
                .user(user)
                .bookmarkId(bookmarkId)
                .fileName(fileSaveRequest.fileName())
                .fileUrl(fileSaveRequest.fileUrl())
                .fileType(fileSaveRequest.fileType())
                .build();
    }

    public FileResponse toFileResponse(File file){
        return FileResponse.builder()
                .fileUrl(file.getFileUrl())
                .fileName(file.getFileName())
                .build();
    }
}
