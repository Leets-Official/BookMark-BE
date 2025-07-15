package leets.bookmark.global.auth.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@AuthenticationPrincipal
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
