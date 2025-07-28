package leets.bookmark.domain.bookmark.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import leets.bookmark.domain.category.domain.entity.Category;

import lombok.*;
import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;

import java.util.List;
import java.util.ArrayList;

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

    private String url;

    private String title;

    private String memo;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(name = "is_saved", nullable = false)
    private boolean isSaved = true;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "bookmark", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookmarkTagMapping> bookmarkTagMappings = new ArrayList<>();

    public void updateBookmark(String title, String memo, String thumbnailUrl, String platform) {
        this.title = title;
        this.memo = memo;
        this.thumbnailUrl = thumbnailUrl;
        this.platform = Platform.from(platform);
    }

    public void addTagMapping(BookmarkTagMapping mapping) {
        bookmarkTagMappings.add(mapping);
        mapping.setBookmark(this);
    }

    public void clearTagMappings() {
        for (BookmarkTagMapping mapping : new ArrayList<>(bookmarkTagMappings)) {
            mapping.setBookmark(null);
        }
        this.bookmarkTagMappings.clear();
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public File getFile() {
        return null;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }
}
