package leets.bookmark.global.auth.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import leets.bookmark.global.auth.jwt.application.dto.JwtTokenDto;
import leets.bookmark.global.auth.jwt.application.dto.JwtTokenReissueRequest;
import leets.bookmark.global.auth.jwt.service.JwtService;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static leets.bookmark.global.auth.presentation.AuthResponseMessage.JWT_TOKEN_REISSUE_SUCCESS;

@Tag(name = "AUTH", description = "사용자 인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/reissue")
    public CommonResponse<JwtTokenDto> reissue(@Validated @RequestBody JwtTokenReissueRequest request) {
        JwtTokenDto response = jwtService.reissueToken(request.refreshToken());
        return CommonResponse.createSuccess(JWT_TOKEN_REISSUE_SUCCESS.getMessage(), response);
    }
}
