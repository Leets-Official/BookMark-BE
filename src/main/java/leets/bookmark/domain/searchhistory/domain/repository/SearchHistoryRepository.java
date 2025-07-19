package leets.bookmark.domain.searchhistory.domain.repository;

import java.util.List;
import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
