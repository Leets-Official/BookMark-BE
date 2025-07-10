package leets.bookmark.domain.bookmark.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor // JPA는 기본 생성자 필요
@Entity
@Table(name = "bookmark")
public class Bookmark {

    @Id
    @Column(name = "bookmark_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
}
