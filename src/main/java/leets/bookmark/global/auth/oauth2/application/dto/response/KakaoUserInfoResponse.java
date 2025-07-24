package leets.bookmark.global.auth.oauth2.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfoResponse (
        @JsonProperty("id")
        Long id,

        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoAccount(

            @JsonProperty("profile")
            Profile profile,

            @JsonProperty("email")
            String email
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Profile(
                @JsonProperty("nickname")
                String nickName,

                @JsonProperty("profile_image_url")
                String profileImageUrl
        ) {}
    }
}
