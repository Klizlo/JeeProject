package kw.pollub.myboardgamelist.controller;

import jakarta.validation.Valid;
import kw.pollub.myboardgamelist.config.UserDetailsImpl;
import kw.pollub.myboardgamelist.dto.BoardGameDtoMapper;
import kw.pollub.myboardgamelist.dto.BoardGameWithCategoryDto;
import kw.pollub.myboardgamelist.exception.UnauthorizedException;
import kw.pollub.myboardgamelist.model.BoardGame;
import kw.pollub.myboardgamelist.model.User;
import kw.pollub.myboardgamelist.service.IBoardGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boardGames")
public class BoardGameController {

    private final IBoardGameService boardGameService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BoardGameWithCategoryDto addBoardGame(@Valid @RequestBody BoardGame boardGame, Authentication authentication) {
        UserDetailsImpl loggedUser = (UserDetailsImpl) authentication.getPrincipal();
        boardGame.setOwner(new User());
        boardGame.getOwner().setId(loggedUser.getId());

        return BoardGameDtoMapper.mapToBoardGameWithCategoryDto(boardGameService.addBoardGame(boardGame));
    }

    @PutMapping("/{boardGameId}")
    public BoardGameWithCategoryDto updateBoardGame(@Valid @RequestBody BoardGame boardGame, @PathVariable("boardGameId") Long boardGameId, Authentication authentication) {
        UserDetailsImpl loggedUser = (UserDetailsImpl) authentication.getPrincipal();
        BoardGame boardGameToEdit = boardGameService.findBoardGameWithCategoryAndOwnerById(boardGameId);

        if (!boardGameToEdit.getOwner().getId().equals(loggedUser.getId())) {
            throw new UnauthorizedException();
        }

        return BoardGameDtoMapper.mapToBoardGameWithCategoryDto(boardGameService.updateBoardGame(boardGame, boardGameToEdit));
    }

    @DeleteMapping("/{boardGameId}")
    public void removeBoardGame(@PathVariable("boardGameId") Long boardGameId, Authentication authentication) {
        UserDetailsImpl loggedUser = (UserDetailsImpl) authentication.getPrincipal();
        BoardGame boardGameToRemove = boardGameService.findBoardGameWithCategoryAndOwnerById(boardGameId);

        if (!boardGameToRemove.getOwner().getId().equals(loggedUser.getId())) {
            throw new UnauthorizedException();
        }

        boardGameService.removeBoardGame(boardGameId);
    }

}
