package leets.bookmark.domain.user.domain.service;

import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserGetService userGetService;

    @Transactional
    public void updateNickname(User user, String newNickname) {
        userGetService.findById(user.getId()).updateNickname(newNickname);
    }
}
