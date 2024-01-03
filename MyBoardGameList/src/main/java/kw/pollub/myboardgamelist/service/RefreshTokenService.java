package kw.pollub.myboardgamelist.service;

import kw.pollub.myboardgamelist.exception.RefreshTokenException;
import kw.pollub.myboardgamelist.model.RefreshToken;
import kw.pollub.myboardgamelist.model.User;
import kw.pollub.myboardgamelist.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService{

    private final RefreshTokenRepository refreshTokenRepository;
    private final IUserService userService;

    @Value("${jwt.refreshToken.expirationDays}")
    private int refreshTokenExpiration;

    @Override
    public RefreshToken findByUserId(Long userId) {
        return refreshTokenRepository.findByUser_Id(userId)
                .orElseThrow(RefreshTokenException::new);
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        User user = userService.findUserById(userId);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(refreshTokenExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Boolean verifyExpirationDate(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.deleteById(refreshToken.getId());
            throw new RefreshTokenException();
        }

        return true;
    }
}
