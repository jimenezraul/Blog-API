package com.raul.blogapi.controller;

import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.model.User;
import com.raul.blogapi.security.TokenGenerator;
import com.raul.blogapi.dto.LoginDTO;
import com.raul.blogapi.dto.SignupDTO;
import com.raul.blogapi.dto.TokenDTO;
import com.raul.blogapi.service.UserService;
import com.raul.blogapi.service.UserServiceImpl.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class Auth {
    @Autowired
    UserManager userDetailsManager;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SignupDTO signupDTO) {
        User user = new User(signupDTO.getName(), signupDTO.getUsername(), signupDTO.getPassword(), signupDTO.getBirthDate());

        if(signupDTO.getName() == null || signupDTO.getUsername() == null || signupDTO.getPassword() == null || signupDTO.getBirthDate() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }

        if(userDetailsManager.userExists(user.getUsername())) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        userDetailsManager.createUser(user);

//        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(), Collections.EMPTY_LIST);

        return ResponseEntity.ok("User created, please login");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(), loginDTO.getPassword()));

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/token")
    public ResponseEntity token(@RequestBody TokenDTO tokenDTO) {
        Authentication authentication = refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));
        Jwt jwt = (Jwt) authentication.getCredentials();
        // check if present in db and not revoked, etc


        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }
}
