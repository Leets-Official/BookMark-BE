package leets.bookmark.domain.searchhistory.domain.repository;

import java.util.List;
import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository {
    List<SearchHistory> findByUserId(Long userId);
}
