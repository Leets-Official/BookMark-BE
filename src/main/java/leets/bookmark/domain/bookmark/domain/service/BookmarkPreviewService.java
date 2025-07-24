package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class BookmarkPreviewService {

  private final RestTemplate restTemplate;

  @Value("${preview.api.url}")
  private String previewApiUrl;

  public BookmarkPreviewService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<BookmarkResponse> extractPreviewFromUrl(String url) {
    BookmarkPreviewResponse response = callPreviewApi(url, previewApiUrl);
    BookmarkResponse preview = BookmarkResponse.builder()
        .title(response.title())
        .thumbnailUrl(response.thumbnailUrl())
        .faviconUrl(response.faviconUrl())
        .build();
    return List.of(preview);
  }

  private BookmarkPreviewResponse callPreviewApi(String url, String endpoint) {
    return restTemplate.postForObject(
        endpoint,
        Map.of("url", url),
        BookmarkPreviewResponse.class
    );
  }
}
