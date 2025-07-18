package leets.bookmark.domain.category.domain.entity;

import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories")
@AllArgsConstructor
@SuperBuilder
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100, nullable = false)
    private String categoryName;

    public void updateName(String newCategoryName) {
        this.categoryName = newCategoryName;
    }
}
