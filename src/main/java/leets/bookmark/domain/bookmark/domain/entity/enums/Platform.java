package leets.bookmark.domain.bookmark.domain.entity.enums;

import java.util.Arrays;

public enum Platform {
    NAVER,
    KAKAO,
    GOOGLE,
    PC,
    MOBILE,
    ETC;

    public static Platform from(String platform) {
        return Arrays.stream(values())
            .filter(p -> p.name().equalsIgnoreCase(platform))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 플랫폼입니다: " + platform));
    }
}
