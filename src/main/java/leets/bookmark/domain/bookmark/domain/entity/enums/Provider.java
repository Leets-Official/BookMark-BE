package leets.bookmark.domain.bookmark.domain.entity.enums;

import leets.bookmark.domain.bookmark.application.exception.InvalidProviderException;
import java.util.Arrays;
public enum Provider {
    NAVER,
    KAKAO,
    GOOGLE;

    public static Provider from(String provider) {
        return Arrays.stream(values())
            .filter(p -> p.name().equalsIgnoreCase(provider))
            .findFirst()
            .orElseThrow(InvalidProviderException::new);
    }
}
