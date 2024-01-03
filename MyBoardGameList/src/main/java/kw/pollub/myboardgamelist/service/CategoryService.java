package kw.pollub.myboardgamelist.service;

import kw.pollub.myboardgamelist.exception.CategoryAlreadyExistsException;
import kw.pollub.myboardgamelist.exception.CategoryNotFoundException;
import kw.pollub.myboardgamelist.model.Category;
import kw.pollub.myboardgamelist.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    @Override
    public List<Category> findAllCategoriesWithBoardGamesByOwnerId(Long userId) {
        return categoryRepository.findAllCategoriesWithBoardGamesByOwnerId(userId);
    }

    @Override
    public Category addCategory(Category category) {

        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistsException(category.getName());
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        //TODO
        return null;
    }

    @Override
    public Category deleteCategory(Long categoryId) {
        //TODO
        return null;
    }
}
