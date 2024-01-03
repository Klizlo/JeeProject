package kw.pollub.myboardgamelist.controller;

import kw.pollub.myboardgamelist.config.UserDetailsImpl;
import kw.pollub.myboardgamelist.dto.*;
import kw.pollub.myboardgamelist.exception.UnauthorizedException;
import kw.pollub.myboardgamelist.service.IBoardGameService;
import kw.pollub.myboardgamelist.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(value = "*", maxAge = 36000)
public class UserController {

    private final IBoardGameService boardGameService;
    private final ICategoryService categoryService;

    @GetMapping("/{userId}/boardGames")
    public List<BoardGameDto> getAllBoardGamesByOwner(
            @PathVariable("userId") Long userId,
            Authentication authentication) {
        UserDetailsImpl loggedUser = (UserDetailsImpl) authentication.getPrincipal();
        if (!userId.equals(loggedUser.getId())) {
            throw new UnauthorizedException();
        }

        return BoardGameDtoMapper
                .mapToBoardGameDtos(boardGameService
                        .findAllBoardGamesByOwner(userId));
    }

    @GetMapping("/{userId}/boardGames/{boardGameId}")
    public BoardGameWithCategoryDto getBoardGameByOwner(
            @PathVariable("userId") Long userId,
            @PathVariable("boardGameId") Long boardGameId,
            Authentication authentication) {
        UserDetailsImpl loggedUser = (UserDetailsImpl) authentication.getPrincipal();
        if (!userId.equals(loggedUser.getId())) {
            throw new UnauthorizedException();
        }

        return BoardGameDtoMapper
                .mapToBoardGameWithCategoryDto(boardGameService
                        .findBoardGameWithCategoryById(boardGameId));
    }

    @GetMapping("/{userId}/boardGames-by-category")
    public List<CategoryWithBoarGamesDto> getAllCategoriesWithBoardGamesByOwner(
            @PathVariable("userId") Long userId,
            Authentication authentication) {
        UserDetailsImpl loggedUser = (UserDetailsImpl) authentication.getPrincipal();
        if (!loggedUser.getId().equals(userId)) {
            throw new UnauthorizedException();
        }

        return CategoryDtoMapper
                .mapToCategoryWithBoarGamesDtos(categoryService
                        .findAllCategoriesWithBoardGamesByOwnerId(userId));
    }

}
