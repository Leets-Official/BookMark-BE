package leets.bookmark.domain.server.domain.repository;

import leets.bookmark.domain.server.domain.entity.SearchHistory;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository {
    List<SearchHistory> findByUserId(Long userId);
}
