package com.raul.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import com.raul.blogapi.model.RefreshTokens;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView(Views.Private.class)
public class RefreshTokensDTO {
    private Long id;
    private String token;
    private Long userId;
    private LocalDate expiryDate;
    private Boolean isRevoked = false;

    public RefreshTokensDTO(RefreshTokens refreshTokens) {
        this.id = refreshTokens.getId();
        this.token = refreshTokens.getToken();
        this.userId = refreshTokens.getUserId();
        this.isRevoked = refreshTokens.getRevoked();
        this.expiryDate = refreshTokens.getExpiryDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getIsRevoked() {
        return isRevoked;
    }
}
