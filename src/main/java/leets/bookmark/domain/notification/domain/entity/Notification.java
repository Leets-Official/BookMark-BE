package leets.bookmark.domain.notification.domain.entity;

import jakarta.persistence.*;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name = "notifications")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    private String title;
    private String description;
    private String fileUrl;

    LocalDateTime notifyAt;

    Boolean isNotified;

    public void setNotified(){
        this.isNotified = true;
    }

}
