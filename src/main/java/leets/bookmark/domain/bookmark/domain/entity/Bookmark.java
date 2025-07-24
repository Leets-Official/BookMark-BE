package leets.bookmark.domain.bookmark.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.notification.domain.entity.Notification;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;

import lombok.*;
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

    private String platform;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updateBookmark(String title, String memo, String fileUrl, FileType fileType, String platform) {
        this.title = title;
        this.memo = memo;
        if (this.file != null) {
            this.file.updateFile(this.file.getFileName(), fileUrl, fileType);
        }
        this.platform = platform;
    }

}
