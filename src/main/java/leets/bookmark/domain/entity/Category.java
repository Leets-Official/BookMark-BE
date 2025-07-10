package leets.bookmark.domain.entity;

import leets.bookmark.global.common.entity.BaseTimeEntity;

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
    // TODO: User 병합 후 복원
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id", nullable = false)
    //private User user;

    @Column(length = 100, nullable = false)
    private String name;

    // @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Tag> tags = new ArrayList<>();

    // @OneToMany(mappedBy = "category")
    // private List<Bookmark> bookmarks = new ArrayList<>();

    @Builder(toBuilder = true)
    public Category(String name) {
        // this.user = user;
        this.name = name;
    }

    public void updateName(String newName) {
        this.name = newName;
    }
}
