package com.raul.blogapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Long userId;
    private LocalDate expiryDate;
    private Boolean isRevoked = false;

    public RefreshTokens(String encodedToken, Long userId) {
        this.token = encodedToken;
        this.userId = userId;
        this.expiryDate = LocalDate.now().plusDays(30);
    }

    public Boolean getRevoked() {
        return isRevoked;
    }
}
