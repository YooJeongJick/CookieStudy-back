package com.example.cookiecookie.service;

import com.example.cookiecookie.core.security.JwtTokenProvider;
import com.example.cookiecookie.entity.UserEntity;
import com.example.cookiecookie.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserEntity findUserByToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        return token == null ? null : userRepository.findByLoginId(jwtTokenProvider.getLoginId(token)).orElse(null);
    }

}
