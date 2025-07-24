package leets.bookmark.domain.searchhistory.domain.repository;

import java.util.List;
import java.util.Optional;
import leets.bookmark.domain.searchhistory.domain.entity.SearchHistory;
import leets.bookmark.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserOrderByCreatedAtDesc(User user);

    Optional<SearchHistory> findByUserAndKeyword(User user, String keyword);

    Optional<SearchHistory> findByIdAndUserId(Long id, Long userId);
}
