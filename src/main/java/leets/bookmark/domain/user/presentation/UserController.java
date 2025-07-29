package leets.bookmark.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.domain.user.application.dto.request.UserNicknameUpdateRequest;
import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.application.usecase.UserUseCase;
import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static leets.bookmark.domain.user.presentation.UserResponseMessage.*;

@Tag(name = "USER", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회 API", description = "JWT로 인증된 사용자 본인의 정보를 반환합니다.")
    public CommonResponse<UserInfoResponse> getUserInfo(@CurrentUser Long userId) {
        UserInfoResponse response = userUseCase.getUserInfo(userId);
        return CommonResponse.createSuccess(GET_USER_INFO_SUCCESS.getMessage(), response);
    }

    @PatchMapping("/me/nickname")
    @Operation(summary = "닉네임 변경 API", description = "사용자가 본인 닉네임을 변경할 수 있도록 하는 API입니다.")
    public CommonResponse<Void> updateUserNickname(
            @CurrentUser Long userId,
            @Validated @RequestBody UserNicknameUpdateRequest request
    ) {
        userUseCase.updateNickname(userId, request.nickname());
        return CommonResponse.createSuccess(UPDATE_USER_NICKNAME_SUCCESS.getMessage());
    }

    @DeleteMapping("/withdraw")
    @Operation(summary = "회원탈퇴 API", description = "사용자가 회원 탈퇴를 요청하는 API입니다.")
    public CommonResponse<Void> withdrawUser(@CurrentUser Long userId) {
        userUseCase.withdraw(userId);
        return CommonResponse.createSuccess(WITHDRAW_USER_SUCCESS.getMessage());
    }
}
