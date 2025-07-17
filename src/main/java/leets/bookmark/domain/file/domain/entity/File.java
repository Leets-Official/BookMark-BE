package leets.bookmark.domain.file.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table(name = "files")
@Getter
@SuperBuilder
@RequiredArgsConstructor
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

    @Column(nullable = false)
    long bookmarkId;    // Bookmark bookmark

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
