package leets.bookmark.domain.file.application.dto.response;

import lombok.Builder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

@Builder
public record FetchedThumbnailResponse(
        InputStreamResource inputStreamResource,
        MediaType mediaType
) {}
