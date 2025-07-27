package leets.bookmark.global.auth.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.domain.user.application.dto.response.UserKakaoLoginResponse;
import leets.bookmark.global.auth.jwt.application.dto.JwtTokenDto;
import leets.bookmark.global.auth.jwt.application.dto.JwtTokenReissueRequest;
import leets.bookmark.global.auth.jwt.service.JwtService;
import leets.bookmark.global.auth.oauth2.application.usecase.OAuth2UseCase;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static leets.bookmark.global.auth.presentation.AuthResponseMessage.*;

@Tag(name = "AUTH", description = "사용자 인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final OAuth2UseCase oAuth2UseCase;

    @PostMapping("/reissue")
    public CommonResponse<JwtTokenDto> reissue(@Validated @RequestBody JwtTokenReissueRequest request) {
        JwtTokenDto response = jwtService.reissueToken(request.refreshToken());
        return CommonResponse.createSuccess(JWT_TOKEN_REISSUE_SUCCESS.getMessage(), response);
    }

    @GetMapping("/login/kakao")
    public CommonResponse<UserKakaoLoginResponse> kakaoLogin(@RequestParam("code") String code) {
        UserKakaoLoginResponse response = oAuth2UseCase.kakaoLogin(code);
        return CommonResponse.createSuccess(KAKAO_LOGIN_SUCCESS.getMessage(), response);
    }
}
