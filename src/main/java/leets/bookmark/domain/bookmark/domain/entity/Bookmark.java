package leets.bookmark.domain.bookmark.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bookmark")
public class Bookmark extends BaseTimeEntity {

    @Id
    @Column(name = "bookmark_id")
    private Long id;

    private String title;

    private String thumbnailUrl;
}
