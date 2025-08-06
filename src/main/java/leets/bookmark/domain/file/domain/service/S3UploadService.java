package leets.bookmark.domain.file.domain.service;

import leets.bookmark.domain.file.application.exception.S3UploadException;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3UploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private final S3Client s3Client;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public String upload(String fileUrl, FileType fileType) {
        try {
            URL url = URI.create(fileUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStream inputStream = connection.getInputStream()) {

                String fileName = generateUrl(fileType);

                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .contentType(connection.getContentType())
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, connection.getContentLengthLong()));

                return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;
            }

        } catch (Exception e){
            throw new S3UploadException();
        }
    }

    private String generateUrl(FileType fileType) {
        String extension = fileType.getExtension();
        return LocalDateTime.now().format(FORMATTER) + UUID.randomUUID().toString() + extension;
    }
}
