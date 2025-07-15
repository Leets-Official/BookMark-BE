package leets.bookmark.domain.user.domain.service;

import leets.bookmark.domain.user.application.exception.UserNotFoundException;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserRepository userRepository;

    @Transactional
    public User updateNickname(User user, String newNickname) {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(UserNotFoundException::new);
        findUser.updateNickname(newNickname);
        return findUser;
    }
}
