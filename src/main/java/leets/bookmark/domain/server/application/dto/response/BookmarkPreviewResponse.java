package leets.bookmark.domain.server.application.dto.response;

public class BookmarkPreviewResponse {
    private String title;
    private String thumbnailUrl;

    public BookmarkPreviewResponse(String title, String thumbnailUrl) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
