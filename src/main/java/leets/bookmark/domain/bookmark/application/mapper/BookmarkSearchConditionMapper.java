package leets.bookmark.domain.bookmark.application.mapper;

import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchCondition;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchRequest;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.domain.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookmarkSearchConditionMapper {

    public BookmarkSearchCondition toBookmarkSearchCondition(BookmarkSearchRequest request, List<Category> categories, List<Tag> categoryWithTags) {
        return BookmarkSearchCondition.builder()
                .categoryWithTags(categoryWithTags)
                .categories(categories)
                .keyword(request.keyword())
                .platforms(request.platforms())
                .build();
    }
}
