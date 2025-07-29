package leets.bookmark.domain.bookmark.domain.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.exception.BookmarkPreviewException;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkPreviewMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

@Service
@RequiredArgsConstructor
public class BookmarkPreviewService {

  private final RestTemplate restTemplate;
  private final BookmarkPreviewMapper bookmarkPreviewMapper;

  @Value("${preview.api.url}")
  private String previewApiUrl;


  public List<BookmarkResponse> extractPreviewFromUrl(String url) {
    BookmarkPreviewResponse response = callPreviewApi(url, previewApiUrl);
    BookmarkResponse preview = bookmarkPreviewMapper.toResponse(response);
    return List.of(preview);
  }

  private BookmarkPreviewResponse callPreviewApi(String url, String endpoint) {
    try {
      String fullUrl = endpoint + "?url=" + UriUtils.encode(url, StandardCharsets.UTF_8);
      return restTemplate.getForObject(fullUrl, BookmarkPreviewResponse.class);
    } catch (Exception e) {
      throw new BookmarkPreviewException();
    }
  }
}
