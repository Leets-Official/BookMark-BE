package leets.bookmark.domain.searchhistory.domain.entity;

import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchHistory extends BaseTimeEntity {
    private Long id;
    private Long userId;
    private String keyword;
}
