package leets.bookmark.domain.file.domain.service;

import leets.bookmark.domain.file.application.dto.request.FileSaveRequest;
import leets.bookmark.domain.file.application.mapper.FileMapper;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;

import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.S3Exception;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
@Service
public class FileSaveService {

    private final FileRepository fileRepository;
    private final S3Client s3Client;
    private final FileMapper fileMapper;

    @Value("${S3_BUCKET}")
    private String bucketName;

    public void save(File file){
        fileRepository.save(file);
    }

    public String upload(@NotNull FileSaveRequest request) {
        if (request.userId() == null || request.bookmarkId() == null) {
            throw new IllegalArgumentException("userId and bookmarkId must not be null");
        }

        File file = fileMapper.toFile(request);

        fileRepository.save(file);
        return request.fileUrl();
    }

    public String upload(@NotNull MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String key = UUID.randomUUID() + "_" + originalFilename;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(multipartFile.getContentType())
                .build();

            s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));

            return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);

        } catch (IOException | S3Exception e) {
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }
}
