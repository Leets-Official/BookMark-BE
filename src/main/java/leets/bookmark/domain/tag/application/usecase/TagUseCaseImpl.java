package leets.bookmark.domain.tag.application.usecase;

import leets.bookmark.domain.category.application.exception.CategoryOwnerMismatchException;
import leets.bookmark.domain.category.domain.entity.Category;
import leets.bookmark.domain.category.domain.service.CategoryGetService;
import leets.bookmark.domain.tag.application.dto.request.TagCreateRequest;
import leets.bookmark.domain.tag.application.exception.DuplicatedTagNameException;
import leets.bookmark.domain.tag.application.exception.TagOwnerMismatchException;
import leets.bookmark.domain.tag.application.mapper.TagMapper;
import leets.bookmark.domain.tag.domain.entity.Tag;
import leets.bookmark.domain.tag.domain.service.TagDeleteService;
import leets.bookmark.domain.tag.domain.service.TagGetService;
import leets.bookmark.domain.tag.domain.service.TagSaveService;
import leets.bookmark.domain.user.domain.entity.User;
import leets.bookmark.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagUseCaseImpl implements TagUseCase {

    private final CategoryGetService categoryGetService;
    private final UserGetService userGetService;
    private final TagGetService tagGetService;
    private final TagSaveService tagSaveService;

    private final TagMapper tagMapper;
    private final TagDeleteService tagDeleteService;

    @Transactional
    @Override
    public void save(Long userId, TagCreateRequest request) {
        User user = userGetService.findById(userId);
        Category category = categoryGetService.findById(request.categoryId());

        validateCategoryOwner(category, user);
        validateTagName(category, request);

        Tag tag = tagMapper.toTagEntity(category, request);
        tagSaveService.save(tag);
    }

    @Transactional
    @Override
    public void delete(Long userId, Long tagId) {
        User user = userGetService.findById(userId);
        Tag tag = tagGetService.findById(tagId);

        validateTagOwner(tag, user);

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

    private void validateTagName(Category category, TagCreateRequest request) {
        if (tagGetService.existsByCategoryAndTagName(category, request.tagName())) {
            throw new DuplicatedTagNameException();
        }
    }
}
