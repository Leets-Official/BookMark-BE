package leets.bookmark.domain.bookmark.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.file.domain.entity.File;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;

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

    @OneToMany(mappedBy = "bookmark", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> bookmarkCategories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
