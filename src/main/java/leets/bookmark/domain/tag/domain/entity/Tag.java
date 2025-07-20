package leets.bookmark.domain.tag.domain.entity;

import leets.bookmark.domain.category.domain.entity.Category;

import jakarta.persistence.*;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tags")
@AllArgsConstructor
@SuperBuilder
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String tagName;

    public void updateTagName(String tagName) {
        this.tagName = tagName;
    }
}
