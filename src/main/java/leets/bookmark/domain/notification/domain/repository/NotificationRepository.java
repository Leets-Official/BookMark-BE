package leets.bookmark.domain.notification.domain.repository;

import leets.bookmark.domain.notification.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByBookmarkId(Long bookmarkId);

    List<Notification> findAllByIsNotifiedFalseAndNotifyAtBetween(LocalDateTime from, LocalDateTime to);
}
