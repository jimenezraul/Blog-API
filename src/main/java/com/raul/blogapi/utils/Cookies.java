package com.raul.blogapi.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class Cookies {

    public static void setTokenCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        setCookie(response, "accessToken", accessToken, 3600);
        setCookie(response, "refreshToken", refreshToken, 86400);
    }

    public static void deleteCookie(HttpServletResponse response) {
        setCookie(response, "accessToken", null, 0);
        setCookie(response, "refreshToken", null, 0);
    }

    private static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
