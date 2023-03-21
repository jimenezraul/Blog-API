package com.raul.blogapi.service.ServiceImpl;

import com.raul.blogapi.dto.RefreshTokensDTO;
import com.raul.blogapi.model.RefreshTokens;
import com.raul.blogapi.repository.RefreshTokenRepository;
import com.raul.blogapi.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RefreshTokenImpl implements RefreshTokenService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Override
    public Long createRefreshToken(String token, Long userId) {
        String encodedToken = passwordEncoder.encode(token);
        Long id = refreshTokenRepository.save(new RefreshTokens(encodedToken, userId)).getId();
        return id;
    }

    @Override
    public RefreshTokensDTO getRefreshToken(String token, Long userId) {
        List<RefreshTokens> refreshTokens = refreshTokenRepository.findByUserId(userId);
        LocalDate today = LocalDate.now();
        for (RefreshTokens refreshToken : refreshTokens) {
            // Delete expired refresh tokens
            if (refreshToken.getExpiryDate().isBefore(today)) {
                refreshTokenRepository.deleteById(refreshToken.getId());
                continue;
            }
            if (passwordEncoder.matches(token, refreshToken.getToken())) {
                return new RefreshTokensDTO(refreshToken);
            }
        }
        return null;
    }

    @Override
    public void deleteRefreshToken(Long id) {
        refreshTokenRepository.deleteById(id);
    }
}
