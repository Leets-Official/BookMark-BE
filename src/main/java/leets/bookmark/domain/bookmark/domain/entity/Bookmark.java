package leets.bookmark.domain.bookmark.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;

import lombok.*;


@Entity
@Table(name = "bookmarks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @NotNull
    @Column(nullable = false, length = 1024)
    private String url;

    private String title;

    private String memo;

    @Column(name = "favicon_url")
    private String faviconUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(mappedBy = "bookmark", fetch = FetchType.LAZY)
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    public void updateBookmark(String title, String memo) {
        if (title != null) {
            this.title = title;
        }
        if (memo != null) {
            this.memo = memo;
        }
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void deleteFile() {
        this.file = null;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updatePlatform(Platform platform) {
        this.platform = platform;
    }

    public void updateFaviconUrl(String s) {
        this.faviconUrl = s;
    }

    public void updateUrl(String url) {
        this.url = url;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }
}
