package leets.bookmark.domain.bookmark.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import leets.bookmark.domain.bookmark.application.dto.request.BookmarkSearchCondition;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.category.domain.entity.QCategory;
import leets.bookmark.domain.file.domain.entity.QFile;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.bookmark.domain.entity.QBookmark;
import leets.bookmark.domain.bookmark.domain.entity.QBookmarkTagMapping;
import leets.bookmark.domain.tag.domain.entity.QTag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QBookmark bookmark = QBookmark.bookmark;
    QBookmarkTagMapping tagMapping = QBookmarkTagMapping.bookmarkTagMapping;
    QTag tag = QTag.tag;
    QCategory category = QCategory.category;
    QFile file = QFile.file;

    @Override
    public Slice<Bookmark> searchWithFilters(Long userId, BookmarkSearchCondition condition, Pageable pageable) {

        BooleanBuilder builder = buildCondition(userId, condition);

        JPQLQuery<Bookmark> baseQuery = queryFactory
                .selectFrom(bookmark)
                .distinct()
                .leftJoin(bookmark.category, category).fetchJoin()
                .leftJoin(bookmark.file, file).fetchJoin()
                .leftJoin(bookmark.bookmarkTagMappings, tagMapping).fetchJoin()
                .leftJoin(tagMapping.tag, tag).fetchJoin()
                .where(builder);

        List<Bookmark> content = baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) content.remove(pageable.getPageSize());

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanBuilder buildCondition(Long userId, BookmarkSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(bookmark.user.id.eq(userId));

        if (condition.platform() != null) {
            builder.and(bookmark.platform.eq(condition.platform()));
        }

        if (condition.keyword() != null && !condition.keyword().isBlank()) {
            String keyword = "%" + condition.keyword().trim() + "%";
            builder.and(
                    bookmark.title.likeIgnoreCase(keyword)
                            .or(bookmark.memo.likeIgnoreCase(keyword))
            );
        }

        BooleanBuilder categoryBuilder = new BooleanBuilder();
        if (condition.categories() != null && !condition.categories().isEmpty()) {
            for (Category category : condition.categories()) {
                categoryBuilder.or(bookmark.category.id.eq(category.getId()));
            }
        }

        BooleanBuilder tagBuilder = new BooleanBuilder();
        if (condition.categoryWithTags() != null && !condition.categoryWithTags().isEmpty()) {
            Map<Long, List<Long>> categoryTagMap = condition.categoryWithTags().stream()
                    .collect(Collectors.groupingBy(
                            tag -> tag.getCategory().getId(),
                            Collectors.mapping(Tag::getId, Collectors.toList())
                    ));

            for (Map.Entry<Long, List<Long>> entry : categoryTagMap.entrySet()) {
                Long categoryId = entry.getKey();
                List<Long> tagIds = entry.getValue();

                BooleanBuilder andBuilder = new BooleanBuilder();
                andBuilder.and(bookmark.category.id.eq(categoryId));
                andBuilder.and(tagMapping.tag.id.in(tagIds));

                tagBuilder.or(andBuilder);
            }
        }

        BooleanBuilder orBuilder = new BooleanBuilder();

        if (categoryBuilder.hasValue()) {
            orBuilder.or(categoryBuilder);
        }
        if (tagBuilder.hasValue()) {
            orBuilder.or(tagBuilder);
        }

        if (orBuilder.hasValue()) {
            builder.and(orBuilder);
        }

        return builder;
    }
}
