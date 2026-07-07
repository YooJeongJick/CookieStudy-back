package com.example.cookiecookie.service;

import com.example.cookiecookie.core.error.ErrorCode;
import com.example.cookiecookie.core.error.exception.NotFoundException;
import com.example.cookiecookie.dto.CookieDto;
import com.example.cookiecookie.entity.CookieEntity;
import com.example.cookiecookie.repository.CookieRepository;
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

    public void createCookie(CookieDto cookieDto) {
        CookieEntity cookie = CookieEntity.builder()
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

    public CookieDto findCookie(Long id) {
        CookieEntity cookie = cookieRepository.findById(id).orElse(null);
        if (cookie == null)
            throw new NotFoundException("존재하지 않는 쿠키", ErrorCode.NOT_FOUND_EXCEPTION);

        return CookieDto.builder()
                .cookieName(cookie.getCookieName())
                .cookieLevel(cookie.getCookieLevel())
                .cookieAttribute(cookie.getCookieAttribute())
                .build();
    }

    public void updateCookie(Long id, CookieDto cookieDto) {
        CookieEntity cookie = cookieRepository.findById(id).orElse(null);
        if (cookie == null)
            throw new NotFoundException("존재하지 않는 쿠키", ErrorCode.NOT_FOUND_EXCEPTION);

        cookie.update(cookieDto);
    }

    public void deleteCookie(Long id) {
        CookieEntity cookie = cookieRepository.findById(id).orElse(null);
        if (cookie == null)
            throw new NotFoundException("존재하지 않는 쿠키", ErrorCode.NOT_FOUND_EXCEPTION);

        cookieRepository.delete(cookie);
    }

}
