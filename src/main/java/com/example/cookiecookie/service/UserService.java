package com.example.cookiecookie.service;

import com.example.cookiecookie.core.security.JwtTokenProvider;
import com.example.cookiecookie.dto.LoginRequestDto;
import com.example.cookiecookie.dto.RegisterRequestDto;
import com.example.cookiecookie.entity.UserEntity;
import com.example.cookiecookie.repository.UserRepository;
import com.example.cookiecookie.validation.UserValidation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserValidation userValidation;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        UserEntity user = userValidation.checkCredentials(loginRequestDto.getLoginId(), loginRequestDto.getPassword());

        String accessToken = jwtTokenProvider.createAccessToken(user.getLoginId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getLoginId());

        user.updateRefreshToken(refreshToken);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
    }

    @Transactional
    public void register(RegisterRequestDto registerRequestDto, HttpServletResponse response) {
        userValidation.checkDuplicate(registerRequestDto.getLoginId(), registerRequestDto.getNickname());

        String accessToken = jwtTokenProvider.createAccessToken(registerRequestDto.getLoginId());
        String refreshToken = jwtTokenProvider.createRefreshToken(registerRequestDto.getLoginId());

        UserEntity user = UserEntity.builder()
                .loginId(registerRequestDto.getLoginId())
                .password(registerRequestDto.getPassword() != null ? passwordEncoder.encode(registerRequestDto.getPassword()) : null)
                .nickname(registerRequestDto.getNickname())
                .refreshToken(refreshToken)
                .build();

        userRepository.save(user);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
    }

}