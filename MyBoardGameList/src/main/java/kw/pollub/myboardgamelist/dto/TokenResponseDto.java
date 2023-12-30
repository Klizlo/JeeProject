package kw.pollub.myboardgamelist.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDto {
    private String token;
    private UserDto user;
}
