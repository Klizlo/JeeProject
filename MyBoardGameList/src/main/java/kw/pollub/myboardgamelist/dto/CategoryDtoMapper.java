package kw.pollub.myboardgamelist.dto;

import kw.pollub.myboardgamelist.model.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDtoMapper {

    public static List<CategoryDto> mapToCategoryDtos(List<Category> categories) {
        return categories.stream()
                .map(CategoryDtoMapper::mapToCategoryDto)
                .toList();
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static List<CategoryWithBoarGamesDto> mapToCategoryWithBoarGamesDtos(List<Category> categories) {
        return categories.stream()
                .map(CategoryDtoMapper::mapToCategoryWithBoarGamesDto)
                .toList();
    }

    private static CategoryWithBoarGamesDto mapToCategoryWithBoarGamesDto(Category category) {
        return CategoryWithBoarGamesDto.builder()
                .id(category.getId())
                .name(category.getName())
                .boardGames(BoardGameDtoMapper.mapToBoardGameDtos(category.getBoardGames()))
                .build();
    }
}
