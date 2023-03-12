package com.raul.blogapi.controller;

import com.raul.blogapi.dto.*;
import com.raul.blogapi.model.User;
import com.raul.blogapi.security.TokenGenerator;
import com.raul.blogapi.service.EmailService;
import com.raul.blogapi.service.RoleService;
import com.raul.blogapi.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class Auth {
    @Autowired
    UserService userDetailsManager;
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
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SignupDTO signupDTO) throws Exception {
        User user = new User(signupDTO.getName(), signupDTO.getUsername(), signupDTO.getEmail(), signupDTO.getPassword(), signupDTO.getBirthDate());

        if(signupDTO.getName() == null || signupDTO.getUsername() == null || signupDTO.getPassword() == null || signupDTO.getBirthDate() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }

        if(userDetailsManager.userExists(user.getUsername())) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        userDetailsManager.createUser(user);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(), Collections.EMPTY_LIST);

        EmailService email = emailService.sendVerificationEmail(user.getEmail(), tokenGenerator.createToken(authentication).getAccessToken());

        if(email == null) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok("User created, please verify your email");
    }

    @PostMapping("/login")
    public ResponseEntity login(HttpServletResponse response, @RequestBody LoginDTO loginDTO) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        User user = (User) authentication.getPrincipal();

        if(!user.getIsEmailVerified()) {
            return ResponseEntity.badRequest().body("Email not verified");
        }
        TokenDTO tokens = tokenGenerator.createToken(authentication);

        setTokenCookies(response, tokens.getAccessToken(), tokens.getRefreshToken());

        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        deleteCookie(response);
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/token")
    public ResponseEntity token(HttpServletResponse response, @CookieValue(name = "refreshToken") String refreshToken) {
        Authentication authentication = refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(refreshToken));
        Jwt jwt = (Jwt) authentication.getCredentials();
        // check if present in db and not revoked, etc

        TokenDTO tokens = tokenGenerator.createToken(authentication);

        setTokenCookies(response, tokens.getAccessToken(), tokens.getRefreshToken());

        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity verify(@PathVariable String token) {
        Authentication authentication = accessTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(token));
        Jwt jwt = (Jwt) authentication.getCredentials();
        // check if present in db and not revoked, etc
        UserDTO user = userDetailsManager.getUserById(Long.valueOf(jwt.getSubject()));
        System.out.println(user);

        user.setIsEmailVerified(true);

        userService.updateUser(user.getId(), user);

        return ResponseEntity.ok("Email verified");

    }

    public void setTokenCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        setCookie(response, "accessToken", accessToken, 3600);
        setCookie(response, "refreshToken", refreshToken, 86400);
    }

    public void deleteCookie(HttpServletResponse response) {
        setCookie(response, "accessToken", null, 0);
        setCookie(response, "refreshToken", null, 0);
    }

    private void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }


}
