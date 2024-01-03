package kw.pollub.myboardgamelist.service;

import kw.pollub.myboardgamelist.model.RefreshToken;

public interface IRefreshTokenService {

    RefreshToken findByUserId(Long userId);
    RefreshToken createRefreshToken(Long userId);
    Boolean verifyExpirationDate(RefreshToken refreshToken);

}
