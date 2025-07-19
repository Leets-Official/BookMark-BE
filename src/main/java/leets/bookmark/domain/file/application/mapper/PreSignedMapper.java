package leets.bookmark.domain.file.application.mapper;

import leets.bookmark.domain.file.application.dto.response.PresignedUrlResponse;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class PreSignedMapper {

    public PresignedUrlResponse toResponse(String fileName, String fileUrl){
        return PresignedUrlResponse.builder()
                .originalFileName(fileName)
                .presignedUrl(fileUrl)
                .build();
    }

}
