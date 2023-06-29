package com.raul.blogapi.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;

public class Cookies {

    public static void setTokenCookies(HttpServletRequest request, HttpServletResponse response, String accessToken, String refreshToken) {
        setCookie(request, response, "accessToken", accessToken, (int) Duration.ofMinutes(15).getSeconds());
        setCookie(request, response, "refreshToken", refreshToken, (int) Duration.ofDays(30).getSeconds());
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        setCookie(request, response, "accessToken", null, 0);
        setCookie(request, response, "refreshToken", null, 0);
    }

    private static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(maxAge);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
    }
}