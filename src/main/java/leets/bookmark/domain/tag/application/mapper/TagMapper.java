package leets.bookmark.domain.tag.application.mapper;

import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.application.dto.request.TagCreateRequest;
import leets.bookmark.domain.tag.application.dto.response.TagResponse;
import leets.bookmark.domain.tag.domain.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagMapper {

    public Tag toTag(Category category, TagCreateRequest request) {
        return Tag.builder()
                .category(category)
                .tagName(request.tagName())
                .build();
    }

    public static TagResponse toTagResponse(Tag tag) {
        return TagResponse.builder()
                .categoryId(tag.getCategory().getId())
                .tagName(tag.getTagName())
                .build();
    }

    public List<TagResponse> toTagResponseList(List<Tag> tags) {
        return tags.stream()
                .map(TagMapper::toTagResponse)
                .toList();
    }
}
