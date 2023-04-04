package com.raul.blogapi.service;

import com.raul.blogapi.dto.RefreshTokensDTO;

public interface RefreshTokenService {
    Long createRefreshToken(String token, Long userId);

    RefreshTokensDTO getRefreshToken(String token, Long userId);

    void deleteRefreshToken(Long id);
}
