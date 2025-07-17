package leets.bookmark.domain.file.application.usecase;

import jakarta.validation.Valid;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.file.application.dto.request.FileUpdateRequest;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.file.application.dto.response.PresignedUrlResponse;
import leets.bookmark.domain.file.application.exception.InvalidFileExtensionException;
import leets.bookmark.domain.file.application.mapper.FileMapper;
import leets.bookmark.domain.file.application.mapper.PreSignedMapper;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.file.domain.service.FileGetService;
import leets.bookmark.domain.file.domain.service.FileSaveService;
import leets.bookmark.domain.file.domain.service.FileUpdateService;
import leets.bookmark.domain.file.domain.service.PreSignedService;
import leets.bookmark.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class FileUseCase {

    private final PreSignedService preSignedService;
    private final FileSaveService fileSaveService;
    private final FileGetService fileGetService;
    private final FileUpdateService fileUpdateService;

    private final FileMapper fileMapper;
    private final PreSignedMapper preSignedMapper;

    public PresignedUrlResponse getPreSignedUrl(String fileName) {
        return preSignedMapper.toResponse(fileName, preSignedService.createPresignedUrl(fileName));
    }

    @Transactional
    public void saveFile(User user, long bookmarkId, @Valid FileSaveRequest fileSaveRequest) {

        FileType fileType = FileType.fromExtension(getExtension(fileSaveRequest.fileName()))
                .orElseThrow(InvalidFileExtensionException::new);
        File file = fileMapper.toFile(user, bookmarkId, fileSaveRequest, fileType);
        fileSaveService.save(file);
    }

    @Transactional(readOnly = true)
    public FileResponse getFile(Long bookmarkId) {
        File file = fileGetService.findByBookmarkId(bookmarkId);
        return fileMapper.toFileResponse(file);
    }

    @Transactional
    public void updateFile(long bookmarkId, @Valid FileUpdateRequest fileUpdateRequest) {
        File file = fileGetService.findByBookmarkId(bookmarkId);
        FileType fileType = FileType.fromExtension(getExtension(fileUpdateRequest.fileName()))
                .orElseThrow(InvalidFileExtensionException::new);

        fileUpdateService.update(file, fileUpdateRequest.fileName(), fileUpdateRequest.fileUrl(), fileType);
    }

    private String getExtension(String fileName){
        if(fileName == null || !fileName.contains(".")){
            throw new InvalidFileExtensionException();
        }
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }
}
