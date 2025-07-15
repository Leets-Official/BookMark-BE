package leets.bookmark.domain.file.domain.entity;

import jakarta.persistence.*;
import leets.bookmark.domain.file.domain.entity.enums.FileType;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@RequiredArgsConstructor
@Entity
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    Long id;

    Long userId;  // User user

    Long bookmarkId;    // Bookmark bookmark

    String fileName;

    String fileUrl;

    @Enumerated(EnumType.STRING)
    FileType fileType;

}
