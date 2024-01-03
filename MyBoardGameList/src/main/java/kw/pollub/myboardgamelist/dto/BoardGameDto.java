package kw.pollub.myboardgamelist.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardGameDto {
    private Long id;
    private String name;
    private String developer;
    private String description;
    private Integer minNumberOfPlayers;
    private Integer maxNumberOfPlayers;
    private Double numberOfHours;
    private String picture;
}
