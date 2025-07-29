package leets.bookmark.domain.user.domain.repository;

import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(String kakaoId);

    Optional<User> findByIdAndStatus(Long id, Status status);
}
