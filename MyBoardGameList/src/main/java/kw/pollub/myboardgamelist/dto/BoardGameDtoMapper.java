package kw.pollub.myboardgamelist.dto;

import kw.pollub.myboardgamelist.model.BoardGame;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardGameDtoMapper {

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

}
