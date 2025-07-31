package leets.bookmark.domain.bookmark.domain.entity.enums;

import lombok.Getter;

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

    Platform(String platformName) {
        this.platformName = platformName;
    }
}
