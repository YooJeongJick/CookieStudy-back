package com.example.cookiecookie.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CookieDto {
    private String cookieName;
    private String cookieLevel;
    private String cookieAttribute;
}
