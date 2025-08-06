package leets.bookmark.domain.file.application.usecase;

import jakarta.validation.Valid;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.file.application.dto.request.FileUpdateRequest;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.file.application.dto.response.PresignedUrlResponse;
import leets.bookmark.domain.file.application.exception.FileOwnerMismatchException;
import leets.bookmark.domain.file.application.exception.InvalidFileExtensionException;
import leets.bookmark.domain.file.application.mapper.FileMapper;
import leets.bookmark.domain.file.application.mapper.PreSignedMapper;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.file.domain.service.*;
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
    private final S3UploadService s3UploadService;
    private final FileSaveService fileSaveService;
    private final FileGetService fileGetService;
    private final FileUpdateService fileUpdateService;
    private final FileDeleteService fileDeleteService;

    private final FileMapper fileMapper;
    private final PreSignedMapper preSignedMapper;

    public PresignedUrlResponse getPreSignedUrl(String fileName) {
        fileName = extractFileNameWithExtension(fileName);
        return preSignedMapper.toResponse(fileName, preSignedService.createPresignedUrl(fileName));
    }

    @Transactional
    public void saveFile(User user, Bookmark bookmark, @Valid FileSaveRequest fileSaveRequest) {
        FileType fileNameType = getValidatedFileType(fileSaveRequest.fileName());
        FileType fileUrlType = getValidatedFileType(fileSaveRequest.fileUrl());
        if (!fileNameType.equals(fileUrlType)){
            throw new InvalidFileExtensionException();
        }

        File file = fileMapper.toFile(user, bookmark, fileSaveRequest, fileUrlType);
        fileSaveService.save(file);
    }

    @Transactional
    public void saveThumbnailFile(User user, Bookmark bookmark, String thumbnailUrl) {
        String fileName = extractFileNameWithExtension(thumbnailUrl);
        FileType type = getValidatedFileType(fileName);

        String s3UrlResponse = s3UploadService.upload(thumbnailUrl, type);

        File file = fileMapper.toThumbnailFile(user, bookmark, fileName, s3UrlResponse, type);
        fileSaveService.save(file);
    }

    @Transactional(readOnly = true)
    public FileResponse getFile(User user, Bookmark bookmark) {
        File file = fileGetService.findByBookmarkId(bookmark.getId());
        validateFileOwner(user, file);

        return fileMapper.toFileResponse(file);
    }

    @Transactional
    public void updateFile(User user, Bookmark bookmark, @Valid FileUpdateRequest fileUpdateRequest) {
        File file = fileGetService.findByBookmarkId(bookmark.getId());
        validateFileOwner(user, file);

        fileUpdateService.update(file, fileUpdateRequest, getValidatedFileType(fileUpdateRequest.fileName()));
    }

    public void updateThumbnailImage(User user, Bookmark bookmark, String thumbnailUrl) {
        File file = fileGetService.findByBookmarkId(bookmark.getId());
        validateFileOwner(user, file);

        String fileName = extractFileNameWithExtension(thumbnailUrl);
        FileType type = getValidatedFileType(fileName);

        String s3UrlResponse = s3UploadService.upload(thumbnailUrl, type);

        fileUpdateService.updateThumbnailImage(file, fileName, s3UrlResponse, type);
    }

    @Transactional
    public void deleteFile(User user, Bookmark bookmark) {
        File file = fileGetService.findByBookmarkId(bookmark.getId());
        validateFileOwner(user, file);

        fileDeleteService.delete(file);
    }

    private void validateFileOwner(User user, File file){
        if(!(user.getId().equals(file.getUser().getId()))){
            throw new FileOwnerMismatchException();
        }
    }

    private String getExtension(String fileName){
        if(fileName == null || !fileName.contains(".")){
            throw new InvalidFileExtensionException();
        }
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    private FileType getValidatedFileType(String fileName){
       return FileType.fromExtension(getExtension(getExtension(fileName)))
                .orElseThrow(InvalidFileExtensionException::new);
    }

    public String extractFileNameWithExtension(String fileName) {
        return FileType.fromFileName(fileName)
                .map(fileType -> {
                    String lowerFileName = fileName.toLowerCase();
                    return fileName.substring(0,
                            lowerFileName.lastIndexOf(fileType.getExtension()) + fileType.getExtension().length());
                })
                .orElseThrow(InvalidFileExtensionException::new);
    }

}
