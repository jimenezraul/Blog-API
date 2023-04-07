package com.raul.blogapi.security;

import com.raul.blogapi.error.UserNotFoundException;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Log4j2
public class AccessTokenFilter extends OncePerRequestFilter {
    private final JwtAuthenticationProvider accessTokenAuthProvider;
    private final UserRepository userRepository;

    public AccessTokenFilter(JwtAuthenticationProvider accessTokenAuthProvider, UserRepository userRepository) {
        this.accessTokenAuthProvider = accessTokenAuthProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<String> accessToken = parseAccessToken(request);

            if(accessToken.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

                User user = this.getUserByAccessToken(accessToken.get());
                if(user != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }


        } catch (Exception e) {
            log.error("cannot set authentication", e);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> parseAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("accessToken")) {
                    return Optional.of(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }

    private User getUserByAccessToken(String accessToken) {
        Authentication authentication = accessTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(accessToken));
        Jwt jwt = (Jwt) authentication.getCredentials();
        return userRepository.findById(Long.valueOf(jwt.getSubject())).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}