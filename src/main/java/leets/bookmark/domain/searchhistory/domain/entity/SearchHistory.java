package leets.bookmark.domain.searchhistory.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import leets.bookmark.global.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "search_history")
public class SearchHistory extends BaseTimeEntity {

    @Id
    @Column(name = "search_history_id")
    private Long id;

    private Long userId;

    private String keyword;
}
