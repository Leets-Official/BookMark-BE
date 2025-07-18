package leets.bookmark.domain.tag.application.usecase;

import leets.bookmark.domain.tag.application.dto.request.TagCreateRequest;
import leets.bookmark.domain.tag.application.dto.response.TagResponse;

import java.util.List;

public interface TagUseCase {

    List<TagResponse> findAllByCategory(Long userId, Long categoryId);

    void save(Long userId, TagCreateRequest request);

    void delete(Long userId, Long tagId);
}
