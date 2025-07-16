package leets.bookmark.domain.user.domain.service;

import leets.bookmark.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    public void updateNickname(User user, String newNickname) {
         user.updateNickname(newNickname);
    }
}
