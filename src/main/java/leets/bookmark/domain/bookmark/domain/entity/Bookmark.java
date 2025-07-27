package leets.bookmark.domain.bookmark.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import leets.bookmark.domain.category.domain.entity.Category;

import lombok.*;
import java.util.List;
import java.util.ArrayList;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;

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

    private String platform;

    @Column(name = "is_saved", nullable = false)
    private boolean isSaved = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void updateBookmark(String title, String memo, File file, String fileUrl, FileType fileType, String platform) {
        this.title = title;
        this.memo = memo;
        if (file != null) {
            file.updateFile(file.getFileName(), fileUrl, fileType);
        }
        this.platform = platform;
    }

    public void markAsSaved() {
        this.isSaved = true;
    }

    public void markAsUnsaved() {
        this.isSaved = false;
    }

    @OneToMany(mappedBy = "bookmark", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookmarkTagMapping> bookmarkTagMappings = new ArrayList<>();

    public void addTagMapping(BookmarkTagMapping mapping) {
        bookmarkTagMappings.add(mapping);
        mapping.setBookmark(this);
    }

    public void clearTagMappings() {
        this.bookmarkTagMappings.clear();
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public File getFile() {
        return this.bookmarkTagMappings.stream()
            .map(BookmarkTagMapping::getFile)
            .findFirst()
            .orElse(null);
    }

    public String getThumbnailUrl() {
        File file = getFile();
        return file != null ? file.getFileUrl() : null;
    }
}
