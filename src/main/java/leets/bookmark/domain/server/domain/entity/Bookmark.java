package leets.bookmark.domain.server.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Bookmark {
    private Long id;
    private String title;
    private String thumbnailUrl;
}
