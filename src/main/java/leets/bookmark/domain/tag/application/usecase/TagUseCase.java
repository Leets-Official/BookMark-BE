package leets.bookmark.domain.tag.application.usecase;

import leets.bookmark.domain.tag.application.dto.request.TagCreateRequest;

public interface TagUseCase {

    void save(Long userId, TagCreateRequest request);
}
