package com.example.cookiecookie.validation;

import com.example.cookiecookie.core.error.ErrorCode;
import com.example.cookiecookie.core.error.exception.NotFoundException;
import com.example.cookiecookie.core.error.exception.UnAuthorizedException;
import com.example.cookiecookie.entity.CookieEntity;
import com.example.cookiecookie.entity.UserEntity;
import com.example.cookiecookie.repository.CookieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieValidation {

    private final CookieRepository cookieRepository;

    public CookieEntity isPresentCookie(Long id) {
        CookieEntity cookie = cookieRepository.findById(id).orElse(null);
        if (cookie == null) {
            throw new NotFoundException("찾을 수 없는 쿠키입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        }

        return cookie;
    }

    public void isValidateCookie(UserEntity user, CookieEntity cookie) {
        if (cookie.getUser() != user) {
            throw new UnAuthorizedException("접근할 수 없는 쿠키입니다", ErrorCode.UNAUTHORIZED_EXCEPTION);
        }
    }

}
