package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.dto.response.GeneralPreviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class BookmarkPreviewService {

  private final RestTemplate restTemplate;

  public BookmarkPreviewService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<BookmarkPreviewResponse> extractPreviewFromUrl(String url) {
    BookmarkPreviewResponse preview;
    String apiEndpoint = "http://localhost:8000/api/v1/preview";
    GeneralPreviewResponse response = callPreviewApi(url, apiEndpoint);
    preview = new BookmarkPreviewResponse(response.title(), response.thumbnailUrl());
    return List.of(preview);
  }

  private GeneralPreviewResponse callPreviewApi(String url, String endpoint) {
    return restTemplate.postForObject(
        endpoint,
        Map.of("url", url),
        GeneralPreviewResponse.class
    );
  }
}
