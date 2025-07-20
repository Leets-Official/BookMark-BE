package leets.bookmark.domain.bookmark.domain.service;

import leets.bookmark.domain.bookmark.application.dto.response.BookmarkPreviewResponse;
import leets.bookmark.domain.bookmark.application.dto.response.GeneralPreviewResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class BookmarkPreviewService {

  private final RestTemplate restTemplate;
  private final String previewApiUrl;

  public BookmarkPreviewService(
      RestTemplate restTemplate,
      @Value("${preview.api.url}") String previewApiUrl) {
    this.restTemplate = restTemplate;
    this.previewApiUrl = previewApiUrl;
  }

  public List<BookmarkPreviewResponse> extractPreviewFromUrl(String url) {
    BookmarkPreviewResponse preview;

    if (isYoutubeUrl(url)) {
      String videoId = extractYoutubeVideoId(url);
      String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
      String title = "YouTube Video (" + videoId + ")";
      preview = new BookmarkPreviewResponse(title, thumbnailUrl);
    } else {
      GeneralPreviewResponse response = callPreviewApi(url);
      preview = new BookmarkPreviewResponse(response.title(), response.thumbnailUrl());
    }
    return List.of(preview);
  }

  private boolean isYoutubeUrl(String url) {
    return url.contains("youtube.com") || url.contains("youtu.be");
  }

  private String extractYoutubeVideoId(String url) {
    if (url.contains("youtube.com")) {
      int index = url.indexOf("v=") + 2;
      if (index > 1 && index + 11 <= url.length()) {
        return url.substring(index, index + 11);
      }
    } else if (url.contains("youtu.be")) {
      int index = url.lastIndexOf("/") + 1;
      if (index > 0 && index + 11 <= url.length()) {
        return url.substring(index, index + 11);
      }
    }
    return "";
  }

  private boolean isInstagramUrl(String url) {
    return url.contains("instagram.com/p/") || url.contains("instagram.com/reel/");
  }

  private GeneralPreviewResponse callPreviewApi(String url) {
    return restTemplate.postForObject(
        previewApiUrl,
        Map.of("url", url),
        GeneralPreviewResponse.class
    );
  }
}
