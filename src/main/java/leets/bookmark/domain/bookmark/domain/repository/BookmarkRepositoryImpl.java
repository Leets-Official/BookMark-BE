package leets.bookmark.domain.bookmark.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Bookmark> findAllWithFilter(Long categoryId, List<String> tagNames) {
        StringBuilder jpql = new StringBuilder("SELECT DISTINCT 북마크 FROM Bookmark 북마크 LEFT JOIN 북마크.tags 태그 WHERE 1=1");

        if (categoryId != null) {
            jpql.append(" AND 북마크.category.id = :categoryId");
        }
        if (tagNames != null && !tagNames.isEmpty()) {
            jpql.append(" AND 태그.name IN :tagNames");
        }

        TypedQuery<Bookmark> query = em.createQuery(jpql.toString(), Bookmark.class);

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }
        if (tagNames != null && !tagNames.isEmpty()) {
            query.setParameter("tagNames", tagNames);
        }

        return query.getResultList();
    }
}
