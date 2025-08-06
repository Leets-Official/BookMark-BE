package leets.bookmark.domain.file.application.usecase;

import jakarta.validation.Valid;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.file.application.dto.request.FileUpdateRequest;
import leets.bookmark.domain.file.application.dto.response.FetchedThumbnailResponse;
import leets.bookmark.domain.file.application.dto.response.FileResponse;
import leets.bookmark.domain.file.application.dto.response.PresignedUrlResponse;
import leets.bookmark.domain.file.application.exception.FileOwnerMismatchException;
import leets.bookmark.domain.file.application.exception.ImageFetchException;
import leets.bookmark.domain.file.application.exception.InvalidFileExtensionException;
import leets.bookmark.domain.file.application.mapper.FileMapper;
import leets.bookmark.domain.file.application.mapper.PreSignedMapper;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.file.domain.service.*;
import leets.bookmark.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

@Slf4j
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

    public FetchedThumbnailResponse getThumbnailImage(String thumbnailUrl) {
        try {
            String fileName = extractFileNameWithExtension(thumbnailUrl);
            FileType fileType = getValidatedFileType(thumbnailUrl);

            URI uri = URI.create(thumbnailUrl);

            URL url = new URL(uri.toASCIIString());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();

            String contentType = Optional.ofNullable(connection.getContentType())
                    .orElse("application/octet-stream");
            MediaType mediaType = MediaType.parseMediaType(contentType);

            return fileMapper.toFetchedThumbnailResponse(new InputStreamResource(inputStream), mediaType);

        } catch (Exception e){
            log.warn("썸네일 이미지를 가져오는 중 오류 발생. URL: {}", thumbnailUrl, e);
            throw new ImageFetchException();
        }
    }

    private void validateFileOwner(User user, File file){
        if(!(user.getId().equals(file.getUser().getId()))){
            throw new FileOwnerMismatchException();
        }
    }

    private FileType getValidatedFileType(String fileName){
       return FileType.fromFileName(fileName)
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
