package kw.pollub.myboardgamelist.controller;

import jakarta.validation.Valid;
import kw.pollub.myboardgamelist.config.JwtUtils;
import kw.pollub.myboardgamelist.dto.TokenResponseDto;
import kw.pollub.myboardgamelist.dto.UserDtoMapper;
import kw.pollub.myboardgamelist.dto.credentials.LoginCredentials;
import kw.pollub.myboardgamelist.dto.credentials.RegisterCredentials;
import kw.pollub.myboardgamelist.model.User;
import kw.pollub.myboardgamelist.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public TokenResponseDto authenticate(@Valid @RequestBody LoginCredentials credentials) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        final User user = userService.findUserByEmail(credentials.getEmail());

        final String token = jwtUtils.generateToken(authenticate);

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

        return TokenResponseDto.builder()
                .token(token)
                .user(UserDtoMapper.mapToStudentDto(loggedUser))
                .build();
    }

}
