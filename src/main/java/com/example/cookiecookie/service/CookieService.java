package com.example.cookiecookie.service;

import com.example.cookiecookie.dto.CookieDto;
import com.example.cookiecookie.entity.CookieEntity;
import com.example.cookiecookie.entity.UserEntity;
import com.example.cookiecookie.repository.CookieRepository;
import com.example.cookiecookie.validation.CookieValidation;
import com.example.cookiecookie.validation.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CookieService {

    private final CookieRepository cookieRepository;

    private final UserValidation userValidation;
    private final CookieValidation cookieValidation;

    public void createCookie(CookieDto cookieDto, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);

        CookieEntity cookie = CookieEntity.builder()
                .user(user)
                .cookieName(cookieDto.getCookieName())
                .cookieLevel(cookieDto.getCookieLevel())
                .cookieAttribute(cookieDto.getCookieAttribute())
                .build();

        cookieRepository.save(cookie);
    }

    public List<CookieDto> findCookies() {
        List<CookieEntity> cookies = cookieRepository.findAll();
        return cookies.stream().map(cookie -> CookieDto.builder()
                        .cookieName(cookie.getCookieName())
                        .cookieLevel(cookie.getCookieLevel())
                        .cookieAttribute(cookie.getCookieAttribute())
                        .build())
                .collect(Collectors.toList());
    }

    public CookieDto findCookie(Long id, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);
        CookieEntity cookie = cookieValidation.isPresentCookie(id);
        cookieValidation.isValidateCookie(user, cookie);

        return CookieDto.builder()
                .cookieName(cookie.getCookieName())
                .cookieLevel(cookie.getCookieLevel())
                .cookieAttribute(cookie.getCookieAttribute())
                .build();
    }

    public void updateCookie(Long id, CookieDto cookieDto, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);
        CookieEntity cookie = cookieValidation.isPresentCookie(id);
        cookieValidation.isValidateCookie(user, cookie);

        cookie.update(cookieDto);
    }

    public void deleteCookie(Long id, HttpServletRequest request) {
        UserEntity user = userValidation.isPresentUser(request);
        CookieEntity cookie = cookieValidation.isPresentCookie(id);
        cookieValidation.isValidateCookie(user, cookie);

        cookieRepository.delete(cookie);
    }

}
