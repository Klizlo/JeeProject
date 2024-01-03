package kw.pollub.myboardgamelist.controller;

import jakarta.validation.Valid;
import kw.pollub.myboardgamelist.config.JwtUtils;
import kw.pollub.myboardgamelist.config.UserDetailsImpl;
import kw.pollub.myboardgamelist.dto.TokenResponseDto;
import kw.pollub.myboardgamelist.dto.UserDtoMapper;
import kw.pollub.myboardgamelist.dto.credentials.LoginCredentials;
import kw.pollub.myboardgamelist.dto.credentials.RegisterCredentials;
import kw.pollub.myboardgamelist.exception.RefreshTokenException;
import kw.pollub.myboardgamelist.model.RefreshToken;
import kw.pollub.myboardgamelist.model.User;
import kw.pollub.myboardgamelist.service.IRefreshTokenService;
import kw.pollub.myboardgamelist.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(value = "*", maxAge = 36000)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final IRefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public TokenResponseDto authenticate(@Valid @RequestBody LoginCredentials credentials) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        final User user = userService.findUserByEmail(credentials.getEmail());

        final String token = jwtUtils.generateToken(authenticate);

        try{
            RefreshToken refreshToken = refreshTokenService.findByUserId(user.getId());
            refreshTokenService.verifyExpirationDate(refreshToken);
        } catch (RefreshTokenException ex) {
            refreshTokenService.createRefreshToken(user.getId());
        }

        return TokenResponseDto.builder()
                .token(token)
                .user(UserDtoMapper.mapToStudentDto(user))
                .build();
    }

    @PostMapping("/signup")
    public TokenResponseDto signup(@Valid @RequestBody RegisterCredentials credentials) {

        User user = new User();
        user.setName(credentials.getName());
        user.setEmail(credentials.getEmail());
        user.setPassword(credentials.getPassword());

        User loggedUser = userService.addUser(user);

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        final String token = jwtUtils.generateToken(authenticate);

        refreshTokenService.createRefreshToken(loggedUser.getId());

        return TokenResponseDto.builder()
                .token(token)
                .user(UserDtoMapper.mapToStudentDto(loggedUser))
                .build();
    }

    @GetMapping("/refresh")
    public TokenResponseDto refreshToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.findByUserId(userDetails.getId());
        refreshTokenService.verifyExpirationDate(refreshToken);

        String token = jwtUtils.generateToken(authentication);
        User loggedUser = userService.findUserById(userDetails.getId());

        return TokenResponseDto.builder()
                .token(token)
                .user(UserDtoMapper.mapToStudentDto(loggedUser))
                .build();
    }

}


