package leets.bookmark.global.auth.jwt.service;

import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.entity.enums.Status;
import leets.bookmark.domain.user.domain.repository.UserRepository;
import leets.bookmark.global.auth.jwt.application.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByIdAndStatus(Long.valueOf(userId), Status.ACTIVE)
                .orElseThrow(UnauthorizedException::new);

        return new CustomUserDetails(user);
    }
}
