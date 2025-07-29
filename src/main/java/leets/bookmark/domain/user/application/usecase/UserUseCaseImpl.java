package leets.bookmark.domain.user.application.usecase;

import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.application.mapper.UserMapper;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import leets.bookmark.domain.user.domain.service.UserUpdateService;
import leets.bookmark.global.auth.jwt.service.AesEncryptor;
import leets.bookmark.global.auth.oauth2.service.KakaoTokenRefreshService;
import leets.bookmark.global.auth.oauth2.service.KakaoUnlinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserGetService userGetService;
    private final UserUpdateService userUpdateService;
    private final UserMapper userMapper;
    private final KakaoUnlinkService kakaoUnlinkService;
    private final AesEncryptor aesEncryptor;
    private final KakaoTokenRefreshService kakaoTokenRefreshService;

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userGetService.findById(userId);
        return userMapper.toUserInfoResponse(user);
    }

    @Transactional
    @Override
    public void updateNickname(Long userId, String newNickname) {
        User user = userGetService.findById(userId);
        userUpdateService.updateNickname(user, newNickname);
    }

    @Transactional
    @Override
    public void withdraw(Long userId) {
        User user = userGetService.findById(userId);
        kakaoTokenRefreshService.refreshAccessToken(user);

        String kakaoAccessToken = aesEncryptor.decrypt(user.getKakaoAccessToken());
        kakaoUnlinkService.unlink(kakaoAccessToken);

        // TODO: 사용자 삭제 or 사용자 soft delete 처리
        user.deleteAllTokens(); // 임시
        log.info(" [UserUseCaseImpl] 회원 탈퇴 완료");
    }
}
