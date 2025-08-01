package leets.bookmark.domain.category.application.mapper;

import leets.bookmark.domain.category.application.dto.request.CategoryCreateRequest;
import leets.bookmark.domain.category.application.dto.response.CategoryResponse;
import leets.bookmark.domain.category.application.dto.response.CategoryWithTagResponse;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.tag.application.dto.response.TagResponse;
import leets.bookmark.domain.tag.application.mapper.TagMapper;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final TagMapper tagMapper;

    public Category toCategory(User user, CategoryCreateRequest request) {
        return Category.builder()
                .user(user)
                .categoryName(request.categoryName())
                .build();
    }

    public static CategoryResponse toResponse(Category category, List<String> thumbnailUrls) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .thumbnailUrls(thumbnailUrls)
                .build();
    }

    public List<CategoryResponse> toCategoryResponseList(List<Category> categories, Map<Long, List<String>> thumbnailMap) {
        return categories.stream()
                .map(category -> {
                    List<String> thumbnailUrls = thumbnailMap.getOrDefault(category.getId(), List.of());
                    return toResponse(category, thumbnailUrls);
                })
                .toList();
    }

    public CategoryWithTagResponse toCategoryWithTagResponse(Category category, List<Tag> tags) {
        List<TagResponse> tagResponse = tagMapper.toTagResponseList(tags);

        return CategoryWithTagResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .tags(tagResponse)
                .build();
    }

    public List<CategoryWithTagResponse> toCategoryWithTagResponseList(List<Category> categories, List<Tag> tags) {
        Map<Long, List<Tag>> tagMap = tags.stream()
                .collect(Collectors.groupingBy(tag -> tag.getCategory().getId()));

        return categories.stream()
                .map(category -> {
                    List<Tag> tagList = tagMap.getOrDefault(category.getId(), List.of());
                    return toCategoryWithTagResponse(category, tagList);
                })
                .toList();
    }
}
