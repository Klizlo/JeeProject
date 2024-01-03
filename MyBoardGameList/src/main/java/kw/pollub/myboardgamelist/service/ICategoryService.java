package kw.pollub.myboardgamelist.service;

import kw.pollub.myboardgamelist.model.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findAllCategories();
    Category findCategoryById(Long categoryId);
    List<Category> findAllCategoriesWithBoardGamesByOwnerId(Long userId);
    Category addCategory(Category category);
    Category updateCategory(Category category, Long categoryId);
    Category deleteCategory(Long categoryId);

}
