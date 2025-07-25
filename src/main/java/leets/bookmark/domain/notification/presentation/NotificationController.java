package leets.bookmark.domain.notification.presentation;

import leets.bookmark.domain.notification.application.dto.response.NotificationResponse;
import leets.bookmark.domain.notification.application.usecase.NotificationUseCase;
import leets.bookmark.global.auth.annotation.CurrentUser;
import leets.bookmark.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@RestController
public class NotificationController {

    private final NotificationUseCase notificationUseCase;

    @GetMapping
    public CommonResponse<NotificationResponse> getNotification(@CurrentUser Long userId, @RequestParam Long bookmarkId){
        NotificationResponse notificationResponse = notificationUseCase.getNotification(userId, bookmarkId);
        return CommonResponse.createSuccess(NotificationResponseMessage.GET_NOTIFICATION_SUCCESS.getMessage(), notificationResponse);
    }

    @DeleteMapping("{notificationId}")
    public CommonResponse<Void> deleteNotification(@CurrentUser Long userId, @PathVariable long notificationId){
        notificationUseCase.deleteNotification(userId, notificationId);
        return CommonResponse.createSuccess(NotificationResponseMessage.DELETE_NOTIFICATION_SUCCESS.getMessage());
    }

}
