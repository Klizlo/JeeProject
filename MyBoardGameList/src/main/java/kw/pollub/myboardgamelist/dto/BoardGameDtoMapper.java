package kw.pollub.myboardgamelist.dto;

import kw.pollub.myboardgamelist.model.BoardGame;
import kw.pollub.myboardgamelist.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
@RequiredArgsConstructor
public class BoardGameDtoMapper {

    private final CategoryRepository categoryRepository;

    public static List<BoardGameDto> mapToBoardGameDtos(List<BoardGame> boardGames) {
        return boardGames.stream()
                .map(BoardGameDtoMapper::mapToBoardGameDto)
                .toList();
    }

    public static BoardGameDto mapToBoardGameDto(BoardGame boardGame) {
        return BoardGameDto.builder()
                .id(boardGame.getId())
                .name(boardGame.getName())
                .developer(boardGame.getDeveloper())
                .description(boardGame.getDescription())
                .minNumberOfPlayers(boardGame.getMinNumberOfPlayers())
                .maxNumberOfPlayers(boardGame.getMaxNumberOfPlayers())
                .numberOfHours(boardGame.getNumberOfHours())
                .picture(boardGame.getPicture())
                .build();
    }

    public static BoardGameWithCategoryDto mapToBoardGameWithCategoryDto(BoardGame boardGame) {
        return BoardGameWithCategoryDto.builder()
                .id(boardGame.getId())
                .name(boardGame.getName())
                .developer(boardGame.getDeveloper())
                .description(boardGame.getDescription())
                .minNumberOfPlayers(boardGame.getMinNumberOfPlayers())
                .maxNumberOfPlayers(boardGame.getMaxNumberOfPlayers())
                .numberOfHours(boardGame.getNumberOfHours())
                .picture(boardGame.getPicture())
                .category(CategoryDtoMapper.mapToCategoryDto(boardGame.getCategory()))
                .build();
    }

    public BoardGame mapToBoardGame(BoardGameWithCategoryDto boardGameDto) {
        return BoardGame.builder()
                .name(boardGameDto.getName())
                .developer(boardGameDto.getDeveloper())
                .description(boardGameDto.getDescription())
                .minNumberOfPlayers(boardGameDto.getMinNumberOfPlayers())
                .maxNumberOfPlayers(boardGameDto.getMaxNumberOfPlayers())
                .numberOfHours(boardGameDto.getNumberOfHours())
                .category(categoryRepository.findById(boardGameDto.getCategory().getId()).orElseThrow())
                .build();
    }

}
