package com.example.cookiecookie.validation;

import com.example.cookiecookie.core.error.ErrorCode;
import com.example.cookiecookie.core.error.exception.NotFoundException;
import com.example.cookiecookie.entity.UserEntity;
import com.example.cookiecookie.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserService userService;

    public UserEntity isPresentUser(HttpServletRequest request) {
        UserEntity user = userService.findUserByToken(request);
        if (user == null) {
            throw new NotFoundException("찾을 수 없는 유저입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        }

        return user;
    }

}
