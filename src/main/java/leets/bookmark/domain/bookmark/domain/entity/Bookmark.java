package leets.bookmark.domain.bookmark.domain.entity;

import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Bookmark extends BaseTimeEntity {
    private Long id;
    private String title;
    private String thumbnailUrl;
}
