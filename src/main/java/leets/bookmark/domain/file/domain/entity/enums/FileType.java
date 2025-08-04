package leets.bookmark.domain.file.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum FileType {
    PNG(".png"),
    JPG(".jpg"),
    JPEG(".jpeg"),
    PDF(".pdf"),
    DOC(".doc"),
    DOCX(".docx"),
    PPTX(".pptx"),
    PPT(".ppt"),
    TXT(".txt"),
    WEBP(".webp");

    private final String extension;

    public static Optional<FileType> fromExtension(String ext){
        if(ext == null){
            return Optional.empty();
        }
        return Stream.of(FileType.values())
                .filter(fileType -> fileType.getExtension().equalsIgnoreCase(ext))
                .findFirst();
    }

    public static Optional<FileType> fromFileName(String fileName) {
        return Stream.of(FileType.values())
                .filter(fileType -> fileName.toLowerCase().contains(fileType.getExtension()))
                .max(Comparator.comparingInt(fileType -> fileName.toLowerCase().lastIndexOf(fileType.getExtension())));
    }
}
