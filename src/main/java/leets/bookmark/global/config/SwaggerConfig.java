package leets.bookmark.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(attachBearerAuthScheme());
    }

    private Info apiInfo() {
        return new Info()
                .title("Bookmark API")
                .description("Bookmark API 문서입니다")
                .version("1.0.0");
    }

    private Components attachBearerAuthScheme() {
        return new Components()
                .addSecuritySchemes("Authorization",
                    new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .in(SecurityScheme.In.HEADER)
                            .name("Authorization")
                            .description("JWT 토큰을 넣어주세요."));
    }
}
