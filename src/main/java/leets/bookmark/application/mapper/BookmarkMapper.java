package leets.bookmark.application.mapper;

import leets.bookmark.application.dto.response.BookmarkResponse;
import leets.bookmark.domain.entity.Bookmark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    @Mapping(source = "category.name", target = "categoryName")
    BookmarkResponse toResponse(Bookmark bookmark);
}
