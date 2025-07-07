package leets.bookmark.domain.server.domain.entity;

public class Bookmark {
    private Long id;
    private String title;
    private String thumbnailUrl;

    public Bookmark(Long id, String title, String thumbnailUrl) {
        this.id = id;
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
