package leets.bookmark.domain.entity;

import leets.bookmark.domain.tag.domain.entity.Tag;
import java.util.ArrayList;
import java.util.List;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories")
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100, nullable = false)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Category(User user, String categoryName, List<Tag> tags) {
        this.user = user;
        this.categoryName = categoryName;
        this.tags = tags != null ? tags : new ArrayList<>();
    }

    public void updateName(String newCategoryName) {
        this.categoryName = newCategoryName;
    }
}
