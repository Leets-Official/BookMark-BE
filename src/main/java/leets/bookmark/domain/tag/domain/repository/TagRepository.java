package leets.bookmark.domain.tag.domain.repository;

import leets.bookmark.domain.tag.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
