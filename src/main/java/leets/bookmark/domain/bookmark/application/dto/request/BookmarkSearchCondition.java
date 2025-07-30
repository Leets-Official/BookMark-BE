package leets.bookmark.domain.bookmark.application.dto.request;

import leets.bookmark.domain.bookmark.domain.entity.enums.Platform;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.domain.entity.Tag;

import java.util.List;

public record BookmarkSearchCondition (
        String keyword,
        Platform platform,
        List<Category> categories,         // 태그 없이 카테고리만 있는 경우
        List<Tag> categoryWithTags
) {}
