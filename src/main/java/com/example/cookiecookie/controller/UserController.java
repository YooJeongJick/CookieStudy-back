package com.example.cookiecookie.controller;

import com.example.cookiecookie.dto.RegisterRequestDto;
import com.example.cookiecookie.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller", description = "유저 API")
public class UserController {

    private final LoginService loginService;

    @Operation(summary = "회원 가입")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto, HttpServletResponse response) {
        loginService.register(registerRequestDto, response);
        return ResponseEntity.ok("가입 성공, 헤더 내 토큰 확인");
    }

}
