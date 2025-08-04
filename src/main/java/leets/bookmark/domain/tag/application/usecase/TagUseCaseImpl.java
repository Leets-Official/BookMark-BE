package leets.bookmark.domain.tag.application.usecase;

import leets.bookmark.domain.bookmark.domain.entity.Bookmark;
import leets.bookmark.domain.bookmark.domain.entity.BookmarkTagMapping;
import leets.bookmark.domain.bookmark.domain.service.BookmarkGetService;
import leets.bookmark.domain.category.application.exception.CategoryOwnerMismatchException;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.category.domain.service.CategoryGetService;
import leets.bookmark.domain.tag.application.dto.request.TagCreateRequest;
import leets.bookmark.domain.tag.application.dto.request.TagNameUpdateRequest;
import leets.bookmark.domain.tag.application.dto.response.TagResponse;
import leets.bookmark.domain.tag.application.exception.DuplicatedTagNameException;
import leets.bookmark.domain.tag.application.exception.TagOnlyUsedException;
import leets.bookmark.domain.tag.application.exception.TagLimitExceedException;
import leets.bookmark.domain.tag.application.exception.TagOwnerMismatchException;
import leets.bookmark.domain.tag.application.mapper.TagMapper;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.service.TagDeleteService;
import leets.bookmark.domain.tag.domain.service.TagGetService;
import leets.bookmark.domain.tag.domain.service.TagSaveService;
import leets.bookmark.domain.tag.domain.service.TagUpdateService;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagUseCaseImpl implements TagUseCase {

    public static final int TAG_LIMIT = 10;

    private final UserGetService userGetService;
    private final CategoryGetService categoryGetService;
    private final TagGetService tagGetService;
    private final TagSaveService tagSaveService;
    private final TagUpdateService tagUpdateService;
    private final TagDeleteService tagDeleteService;
    private final BookmarkGetService bookmarkGetService;

    private final TagMapper tagMapper;

    @Transactional(readOnly = true)
    @Override
    public List<TagResponse> findAllByCategory(Long userId, Long categoryId) {
        User user = userGetService.findById(userId);
        Category category = categoryGetService.findById(categoryId);

        validateCategoryOwner(category, user);

        List<Tag> tags = tagGetService.findAllByCategory(category);
        return tagMapper.toTagResponseList(tags);
    }

    @Transactional
    @Override
    public void save(Long userId, TagCreateRequest request) {
        User user = userGetService.findById(userId);
        Category category = categoryGetService.findById(request.categoryId());

        validateCategoryOwner(category, user);
        checkExceededTagLimit(category);
        checkDuplicateTagName(category, request.tagName());

        Tag tag = tagMapper.toTag(category, request);
        tagSaveService.save(tag);
    }

    @Transactional
    @Override
    public void update(Long userId, Long tagId, TagNameUpdateRequest request) {
        User user = userGetService.findById(userId);
        Tag tag = tagGetService.findById(tagId);

        validateTagOwner(tag, user);
        checkDuplicateTagName(tag.getCategory(), request.tagName());

        tagUpdateService.update(tag, request);
    }

    @Transactional
    @Override
    public void delete(Long userId, Long tagId) {
        User user = userGetService.findById(userId);
        Tag tag = tagGetService.findById(tagId);

        validateTagOwner(tag, user);
        checkTagCanBeDeleted(tag);

        tagDeleteService.delete(tag);
    }

    private void validateTagOwner(Tag tag, User user) {
        if (!tag.getCategory().getUser().equals(user)) {
            throw new TagOwnerMismatchException();
        }
    }

    private void validateCategoryOwner(Category category, User user) {
        if (!category.getUser().equals(user)) {
            throw new CategoryOwnerMismatchException();
        }
    }

    private void checkDuplicateTagName(Category category, String tagName) {
        if (tagGetService.existsByCategoryAndTagName(category, tagName)) {
            throw new DuplicatedTagNameException();
        }
    }

    private void checkExceededTagLimit(Category category) {
        if (tagGetService.countByCategory(category) >= TAG_LIMIT) {
            throw new TagLimitExceedException();
        }
    }

    private void checkTagCanBeDeleted(Tag tag) {
        List<BookmarkTagMapping> mappings = bookmarkGetService.getBookmarksByTag(tag);

        for (BookmarkTagMapping mapping : mappings) {
            Bookmark bookmark = mapping.getBookmark();
            List<BookmarkTagMapping> bookmarkMappings = bookmarkGetService.getMappingsByBookmark(bookmark);

            if (bookmarkMappings.size() == 1) { // 북마크가 해당 태그 1개만 사용할 경우 삭제 불가
                throw new TagOnlyUsedException();
            }
        }
    }
}
