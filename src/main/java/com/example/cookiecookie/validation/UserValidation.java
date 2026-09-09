package com.example.cookiecookie.validation;

import com.example.cookiecookie.core.error.ErrorCode;
import com.example.cookiecookie.core.error.exception.DuplicateException;
import com.example.cookiecookie.core.error.exception.NotFoundException;
import com.example.cookiecookie.core.security.JwtTokenProvider;
import com.example.cookiecookie.entity.UserEntity;
import com.example.cookiecookie.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserEntity checkUser(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        if (token == null) {
            throw new NotFoundException("찾을 수 없는 유저입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        }

        return checkUserByLoginId(jwtTokenProvider.getLoginId(token));
    }

    public UserEntity checkCredentials(String loginId, String password) {
        UserEntity user = checkUserByLoginId(loginId);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("찾을 수 없는 유저입니다", ErrorCode.NOT_FOUND_EXCEPTION);
        }

        return user;
    }

    public void checkDuplicate(String loginId, String nickname) {

        if (userRepository.existsByLoginId(loginId)) {
            throw new DuplicateException("중복되는 아이디입니다", ErrorCode.DUPLICATE_EXCEPTION);
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicateException("중복되는 닉네임입니다", ErrorCode.DUPLICATE_EXCEPTION);
        }

    }

    private UserEntity checkUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NotFoundException("찾을 수 없는 유저입니다", ErrorCode.NOT_FOUND_EXCEPTION));
    }
}