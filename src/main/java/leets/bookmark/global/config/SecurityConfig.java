package leets.bookmark.global.config;

import leets.bookmark.global.auth.jwt.filter.JwtAuthenticationFilter;
import leets.bookmark.global.auth.jwt.service.CustomUserDetailsService;
import leets.bookmark.global.auth.jwt.service.JwtProvider;
import leets.bookmark.global.auth.oauth2.service.CustomOAuth2UserService;
import leets.bookmark.global.auth.oauth2.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/health-check").permitAll()
                                .requestMatchers("/oauth/**", "/auth/login/**").permitAll()
                                .requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class
                )
                .oauth2Login(oauth2 -> oauth2
                                .authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorize"))
                                .redirectionEndpoint(endpoint -> endpoint.baseUri("/auth/login/*"))
                                .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                )
                .build();
    }
}