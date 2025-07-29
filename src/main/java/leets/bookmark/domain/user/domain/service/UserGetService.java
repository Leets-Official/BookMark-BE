package leets.bookmark.domain.user.domain.service;

import leets.bookmark.domain.user.application.exception.UserNotFoundException;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.entity.enums.Status;
import leets.bookmark.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGetService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findByIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(UserNotFoundException::new);
    }
}
