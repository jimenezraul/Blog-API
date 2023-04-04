package com.raul.blogapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class Csrf {
    @RequestMapping("/csrf")
    public Map<String, String> generateCsrfToken(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrfToken = new HttpSessionCsrfTokenRepository().generateToken(request);
        response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        return Collections.singletonMap("token", csrfToken.getToken());
    }
}
