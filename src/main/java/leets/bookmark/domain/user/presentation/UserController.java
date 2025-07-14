package leets.bookmark.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.domain.user.application.dto.response.UserInfoResponse;
import leets.bookmark.domain.user.application.usecase.UserUseCase;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static leets.bookmark.domain.user.presentation.UserResponseMessage.*;

@Tag(name = "USER", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회 API", description = "JWT로 인증된 사용자 본인의 정보를 반환합니다.")
    public CommonResponse<UserInfoResponse> getUserInfo(@CurrentUser User user) {
        UserInfoResponse response = userUseCase.getUserInfo(user);
        return CommonResponse.createSuccess(GET_USER_INFO_SUCCESS.getMessage(), response);
    }
}
