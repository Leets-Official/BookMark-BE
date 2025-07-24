package leets.bookmark.domain.file.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table(name = "files")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_id", nullable = false)
    Bookmark bookmark;

    @NotNull
    @Column(nullable = false)
    String fileName;

    @NotNull
    @Column(nullable = false)
    String fileUrl;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    FileType fileType;

    public void updateFile(String fileName, String fileUrl, FileType fileType){
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

}
