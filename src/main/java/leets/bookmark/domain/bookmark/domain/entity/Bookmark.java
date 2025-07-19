package leets.bookmark.domain.bookmark.domain.entity;

import jakarta.persistence.*;
import leets.bookmark.domain.entity.Category;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.global.common.entity.BaseTimeEntity;
//import leets.bookmark.domain.entity.File;
import lombok.*;
import java.util.List;
import java.util.ArrayList;
import leets.bookmark.domain.tag.domain.entity.Tag;

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

    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToMany
    @JoinTable(
        name = "bookmark_tag",
        joinColumns = @JoinColumn(name = "bookmark_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
}
