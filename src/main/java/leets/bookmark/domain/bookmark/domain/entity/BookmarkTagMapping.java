package leets.bookmark.domain.bookmark.domain.entity;

import jakarta.persistence.*;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.*;

@Entity
@Table(name = "bookmark_tag_mappings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class BookmarkTagMapping extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_tag_mapping_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_id", nullable = false)
    private Bookmark bookmark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    public static BookmarkTagMapping of(Tag tag, Bookmark bookmark) {
        return BookmarkTagMapping.builder()
            .tag(tag)
            .bookmark(bookmark)
            .build();
    }
}
