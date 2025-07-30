package leets.bookmark.domain.bookmark.domain.entity.enums;

import lombok.Getter;

@Getter
public enum Platform {
    Naver("네이버"),
    Tistory("티스토리"),
    Youtube("유튜브"),
    Instagram("인스타그램"),
    Google("구글");

    private final String platformName;

    Platform(String platformName) {
        this.platformName = platformName;
    }
}
