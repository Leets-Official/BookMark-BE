package leets.bookmark.global.auth.jwt.service;

import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import leets.bookmark.domain.user.domain.service.UserSaveService;
import leets.bookmark.global.auth.jwt.application.dto.JwtTokenDto;
import leets.bookmark.global.auth.jwt.application.exception.JwtTokenInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.refresh.reissue-threshold}")
    private Long refreshReissueThreshold;

    private final JwtProvider jwtProvider;
    private final UserGetService userGetService;
    private final UserSaveService userSaveService;

    @Transactional
    public JwtTokenDto reissueToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new JwtTokenInvalidException();
        }

        Long userId = jwtProvider.extractUserId(refreshToken);
        User user = userGetService.findById(userId);

        validateRefreshTokenOwner(refreshToken, user);

        String reissuedAccessToken = jwtProvider.createAccessToken(user);

        long refreshTokenRemainingTime = jwtProvider.extractTokenRemainingTime(refreshToken);

        if (refreshTokenRemainingTime >= 0 && refreshTokenRemainingTime < refreshReissueThreshold) {
            String reissuedRefreshToken = jwtProvider.createRefreshToken(user);
            user.updateTokens(reissuedAccessToken, reissuedRefreshToken);
        } else {
            user.updateJwtAccessToken(reissuedAccessToken);
        }

        userSaveService.save(user);

        return JwtTokenDto.builder()
                .accessToken(reissuedAccessToken)
                .refreshToken(user.getJwtRefreshToken())
                .build();
    }

    private void validateRefreshTokenOwner(String refreshToken, User user) {
        if (!refreshToken.equals(user.getJwtRefreshToken())) {
            throw new JwtTokenInvalidException();
        }
    }
}
