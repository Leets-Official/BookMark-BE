package leets.bookmark.domain.user.domain.service;

import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUpdateService {

    public void updateNickname(User user, String newNickname) {
         user.updateNickname(newNickname);
    }
}
