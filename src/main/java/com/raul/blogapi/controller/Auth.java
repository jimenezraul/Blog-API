package com.raul.blogapi.controller;

import com.raul.blogapi.dto.*;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.UserRepository;
import com.raul.blogapi.security.TokenGenerator;
import com.raul.blogapi.service.EmailService;
import com.raul.blogapi.service.RefreshTokenService;
import com.raul.blogapi.service.RoleService;
import com.raul.blogapi.service.UserService;
import com.raul.blogapi.utils.Cookies;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class Auth {
    @Autowired
    UserService userService;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;

    @Autowired
    @Qualifier("jwtAccessTokenAuthProvider")
    JwtAuthenticationProvider accessTokenAuthProvider;

    @Autowired
    EmailService emailService;

    @Autowired
    RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SignupDTO signupDTO) throws Exception {
        User user = new User(signupDTO.getName(), signupDTO.getUsername(), signupDTO.getEmail(), signupDTO.getPassword(), signupDTO.getBirthDate());

        if (signupDTO.getName() == null || signupDTO.getUsername() == null || signupDTO.getPassword() == null || signupDTO.getBirthDate() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }

        if (userService.userExists(user.getUsername())) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        userService.createUser(user);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(), Collections.EMPTY_LIST);

        EmailService email = emailService.sendVerificationEmail(user.getEmail(), tokenGenerator.createToken(authentication).getAccessToken());

        if (email == null) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok("User created, please verify your email");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response, @RequestBody LoginDTO loginDTO) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        User user = (User) authentication.getPrincipal();

        if (!user.getIsEmailVerified()) {
            return ResponseEntity.badRequest().body("Email not verified");
        }
        TokenDTO tokens = tokenGenerator.createToken(authentication);

        refreshTokenService.createRefreshToken(tokens.getRefreshToken(), user.getId());

        tokens.setIsLogged(true);
        User loggedUser = userRepository.findById(user.getId()).get();
        loggedUser.getRoles().forEach(role -> {
            if (role.getName().equals("ROLE_ADMIN")) {
                tokens.setIsAdmin(true);
            }
        });

        Cookies.setTokenCookies(response, tokens.getAccessToken(), tokens.getRefreshToken());

        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response, @CookieValue(name = "refreshToken") String refreshToken) {
        Authentication authentication = refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(refreshToken));
        Jwt jwt = (Jwt) authentication.getCredentials();

        RefreshTokensDTO token = refreshTokenService.getRefreshToken(refreshToken, Long.valueOf(jwt.getSubject()));

        refreshTokenService.deleteRefreshToken(token.getId());
        Cookies.deleteCookie(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity token(HttpServletResponse response, @CookieValue(name = "refreshToken") String refreshToken) {
        Authentication authentication = refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(refreshToken));
        Jwt jwt = (Jwt) authentication.getCredentials();

        // check if present in db and not revoked, etc
        RefreshTokensDTO token = refreshTokenService.getRefreshToken(refreshToken, Long.valueOf(jwt.getSubject()));

        if(token == null || token.getIsRevoked()) {
            Cookies.deleteCookie(response);
            return ResponseEntity.badRequest().body("Refresh token not found");
        }

        Instant now = Instant.now();
        Instant expiresAt = jwt.getExpiresAt();
        Duration duration = Duration.between(now, expiresAt);
        long daysUntilExpired = duration.toDays();
        TokenDTO tokens = tokenGenerator.createToken(authentication);

        if (daysUntilExpired < 7) {
            // delete old refresh token and create new one
            refreshTokenService.deleteRefreshToken(token.getId());
            refreshTokenService.createRefreshToken(tokens.getRefreshToken(), Long.valueOf(jwt.getSubject()));
        }

        Cookies.setTokenCookies(response, tokens.getAccessToken(), tokens.getRefreshToken());

        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity verify(@PathVariable String token) {
        Authentication authentication = accessTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(token));
        Jwt jwt = (Jwt) authentication.getCredentials();
        // check if present in db and not revoked, etc
        UserDTO user = userService.getUserById(Long.valueOf(jwt.getSubject()));
        System.out.println(user);

        user.setIsEmailVerified(true);

        userService.updateUser(user.getId(), user);

        return ResponseEntity.ok("Email verified");

    }

    @PostMapping("/resend-verification-email")
    public ResponseEntity resendVerificationEmail(@RequestBody ResendVerificationEmailDTO data) throws Exception {
        User user = userRepository.findUserByEmail(data.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (user.getIsEmailVerified()) {
            return ResponseEntity.badRequest().body("Email already verified");
        }

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, user.getPassword(), Collections.EMPTY_LIST);

        EmailService emailResend = emailService.sendVerificationEmail(user.getEmail(), tokenGenerator.createToken(authentication).getAccessToken());

        if (emailResend == null) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok("Email sent");
    }

}
