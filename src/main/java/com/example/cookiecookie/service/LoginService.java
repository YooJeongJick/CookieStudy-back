package com.example.cookiecookie.service;

import com.example.cookiecookie.core.error.ErrorCode;
import com.example.cookiecookie.core.error.exception.DuplicateException;
import com.example.cookiecookie.core.security.JwtTokenProvider;
import com.example.cookiecookie.dto.RegisterRequestDto;
import com.example.cookiecookie.entity.UserEntity;
import com.example.cookiecookie.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequestDto registerRequestDto, HttpServletResponse response) {
        if (userRepository.existsByLoginId(registerRequestDto.getLoginId())) {
            throw new DuplicateException("Duplicated loginId", ErrorCode.DUPLICATE_EXCEPTION);
        } else if (userRepository.existsByNickname(registerRequestDto.getNickname())) {
            throw new DuplicateException("Duplicated nickName", ErrorCode.DUPLICATE_EXCEPTION);
        }

        String accessToken = jwtTokenProvider.createAccessToken(registerRequestDto.getLoginId());
        String refreshToken = jwtTokenProvider.createRefreshToken(registerRequestDto.getLoginId());

        UserEntity user = UserEntity.builder()
                .loginId(registerRequestDto.getLoginId())
                .password(registerRequestDto.getPassword() != null ? passwordEncoder.encode(registerRequestDto.getPassword()) : null)
                .nickname(registerRequestDto.getNickname())
                .build();

        userRepository.save(user);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
    }

}
