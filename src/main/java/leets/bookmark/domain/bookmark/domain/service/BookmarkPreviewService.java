package leets.bookmark.domain.bookmark.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.exception.BookmarkPreviewException;
import leets.bookmark.domain.bookmark.application.mapper.BookmarkPreviewMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkPreviewService {

  private final RestTemplate restTemplate;
  private final BookmarkPreviewMapper bookmarkPreviewMapper;

  @Value("${preview.api.url}")
  private String previewApiUrl;


  public List<BookmarkPreviewResponse> extractPreviewFromUrl(String url) {
    BookmarkPreviewResponse response = callPreviewApi(url, previewApiUrl);
    BookmarkPreviewResponse preview = bookmarkPreviewMapper.toResponse(response);
    return List.of(preview);
  }

  private BookmarkPreviewResponse callPreviewApi(String url, String endpoint) {
    try {
      String fullUrl = endpoint + "?url=" + url;
      return restTemplate.getForObject(fullUrl, BookmarkPreviewResponse.class);
    } catch (Exception e) {
      log.error("미리보기 API 호출 실패 | url: {}, endpoint: {}, message: {}", url, endpoint, e.getMessage(), e);
      throw new BookmarkPreviewException();
    }
  }
}
