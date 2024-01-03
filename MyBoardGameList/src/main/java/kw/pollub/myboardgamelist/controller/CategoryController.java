package kw.pollub.myboardgamelist.controller;

import kw.pollub.myboardgamelist.dto.CategoryDto;
import kw.pollub.myboardgamelist.dto.CategoryDtoMapper;
import kw.pollub.myboardgamelist.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@CrossOrigin(value = "*", maxAge = 36000)
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("")
    public List<CategoryDto> getAllCategories() {
        return CategoryDtoMapper.mapToCategoryDtos(categoryService.findAllCategories());
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategoryById(@PathVariable("categoryId") Long categoryId) {
        return CategoryDtoMapper.mapToCategoryDto(categoryService.findCategoryById(categoryId));
    }
}
