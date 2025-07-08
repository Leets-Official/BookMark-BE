package leets.bookmark.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    //private User user;

    @Column(length = 100, nullable = false)
    private String name;

    // @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Tag> tags = new ArrayList<>();

    // @OneToMany(mappedBy = "category")
    // private List<Bookmark> bookmarks = new ArrayList<>();

    @Builder
    public Category(String name) {
        // this.user = user;
        this.name = name;
    }

    public void updateName(String newName) {
        this.name = newName;
    }
}
