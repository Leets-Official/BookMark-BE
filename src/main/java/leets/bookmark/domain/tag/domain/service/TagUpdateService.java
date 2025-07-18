package leets.bookmark.domain.tag.domain.service;

import leets.bookmark.domain.tag.application.dto.request.TagNameUpdateRequest;
import leets.bookmark.domain.tag.domain.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagUpdateService {

    public void update(Tag tag, TagNameUpdateRequest request) {
        tag.updateTagName(request.tagName());
    }
}
