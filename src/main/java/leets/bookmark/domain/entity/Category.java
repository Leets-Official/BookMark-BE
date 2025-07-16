package leets.bookmark.domain.entity;

import leets.bookmark.global.common.entity.BaseTimeEntity;
//import leets.bookmark.domain.entity.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id", nullable = false)
    //private User user;

    @Column(length = 100, nullable = false)
    private String categoryName;

    // @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Tag> tags = new ArrayList<>();

    // @OneToMany(mappedBy = "category")
    // private List<Bookmark> bookmarks = new ArrayList<>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public void updateName(String newCategoryName) {
        this.categoryName = newCategoryName;
    }
}
