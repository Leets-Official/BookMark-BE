package leets.bookmark.domain.tag.application.mapper;

import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.application.dto.request.TagCreateRequest;
import leets.bookmark.domain.tag.application.dto.response.TagResponse;
import leets.bookmark.domain.tag.domain.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public Tag toTagEntity(Category category, TagCreateRequest request) {
        return Tag.builder()
                .category(category)
                .tagName(request.tagName())
                .build();
    }

    public TagResponse toTagResponse(Tag tag) {
        return TagResponse.builder()
                .categoryId(tag.getCategory().getId())
                .tagName(tag.getTagName())
                .build();
    }
}
