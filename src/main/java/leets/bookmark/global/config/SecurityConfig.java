package leets.bookmark.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import leets.bookmark.global.auth.jwt.filter.JwtAuthenticationFilter;
import leets.bookmark.global.auth.jwt.service.CustomUserDetailsService;
import leets.bookmark.global.auth.jwt.service.JwtProvider;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String CONTENT_TYPE = "application/json";
    public static final String UTF_8 = "UTF-8";
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/health-check").permitAll()
                                .requestMatchers("/oauth/**", "/auth/login/**").permitAll()
                                .requestMatchers("/api/v1/auth/login/kakao", "/api/v1/auth/reissue").permitAll()
                                .requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .build();
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            setResponse(response, 401, "유효하지 않은 인증입니다.");
        };
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            setResponse(response, 403, "접근 권한이 없습니다.");
        };
    }

    private void setResponse(HttpServletResponse response, int errorCode, String message) throws IOException {
        response.setStatus(errorCode);
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(UTF_8);

        CommonResponse<Object> body = CommonResponse.createSuccess(message);
        String json = objectMapper.writeValueAsString(body);
        response.getWriter().write(json);
    }
}
