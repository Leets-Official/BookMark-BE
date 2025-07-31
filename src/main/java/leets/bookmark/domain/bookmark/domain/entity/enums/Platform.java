package leets.bookmark.domain.bookmark.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import leets.bookmark.domain.bookmark.application.exception.InvalidPlatformException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Platform {
    NAVER("네이버"),
    NAVER_BLOG("네이버 블로그"),
    TISTORY("티스토리"),
    YOUTUBE("YouTube"),
    INSTAGRAM("Instagram"),
    VELOG("velog.io"),
    ETC("기타");

    private final String platformName;

    @JsonCreator
    public static Platform parsing(String inputValue) {
        if (inputValue == null) {
            return null;
        }
        return Stream.of(Platform.values())
                .filter(p -> p.name().equalsIgnoreCase(inputValue)
                        || p.platformName.equalsIgnoreCase(inputValue))
                .findFirst()
                .orElseThrow(InvalidPlatformException::new);
    }
}
