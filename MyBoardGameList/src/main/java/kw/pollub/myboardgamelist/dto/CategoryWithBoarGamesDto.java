package kw.pollub.myboardgamelist.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryWithBoarGamesDto {
    private Long id;
    private String name;
    private List<BoardGameDto> boardGames;
}
