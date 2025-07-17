package leets.bookmark.domain.file.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileResponseCode {
    PRE_SIGNED_URL_SUCCESS("파일 업로드 URL이 생성되었습니다.");

    private final String message;
}
