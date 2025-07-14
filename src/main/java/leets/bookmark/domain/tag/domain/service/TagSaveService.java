package leets.bookmark.domain.tag.domain.service;

import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagSaveService {

    private final TagRepository tagRepository;

    public void save(Tag tag) {
        tagRepository.save(tag);
    }
}
