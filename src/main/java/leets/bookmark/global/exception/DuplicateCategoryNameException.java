package leets.bookmark.global.exception;

public class DuplicateCategoryNameException extends RuntimeException {
    public DuplicateCategoryNameException(String name) {
        super("이미 존재하는 카테고리 이름입니다: " + name);
    }
}
