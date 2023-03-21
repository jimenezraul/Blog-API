package com.raul.blogapi.repository;

import com.raul.blogapi.dto.RefreshTokensDTO;
import com.raul.blogapi.model.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokens, Long> {
    List<RefreshTokens> findByUserId(Long userId);
}
