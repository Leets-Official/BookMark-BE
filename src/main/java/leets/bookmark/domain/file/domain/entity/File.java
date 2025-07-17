package leets.bookmark.domain.file.domain.entity;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    Long bookmarkId;    // Bookmark bookmark

    String fileName;

    String fileUrl;

    @Enumerated(EnumType.STRING)
    FileType fileType;

    public void updateFile(String fileName, String fileUrl, FileType fileType){
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

}
