package com.example.cookiecookie.controller;

import com.example.cookiecookie.dto.CookieDto;
import com.example.cookiecookie.service.CookieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cookie")
@Tag(name = "Cookie Controller", description = "쿠키 API")
public class CookieController {

    private final CookieService cookieService;

    @Operation(summary = "쿠키 생성")
    @PostMapping()
    public ResponseEntity<String> createCookie(@RequestBody CookieDto cookieDto) {
        cookieService.createCookie(cookieDto);
        return ResponseEntity.ok("쿠키 생성 완료");
    }

    @Operation(summary = "전체 쿠키 조회")
    @GetMapping()
    public ResponseEntity<List<CookieDto>> findCookies() {
        List<CookieDto> cookies = cookieService.findCookies();
        return ResponseEntity.ok(cookies);
    }

    @Operation(summary = "개별 쿠키 조회")
    @GetMapping("/{id}")
    public ResponseEntity<CookieDto> findCookie(@PathVariable Long id) {
        CookieDto cookie = cookieService.findCookie(id);
        return ResponseEntity.ok(cookie);
    }

    @Operation(summary = "쿠키 수정")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCookie(@PathVariable Long id, @RequestBody CookieDto cookieDto) {
        cookieService.updateCookie(id, cookieDto);
        return ResponseEntity.ok("쿠키 수정 완료");
    }

    @Operation(summary = "쿠키 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCookie(@PathVariable Long id) {
        cookieService.deleteCookie(id);
        return ResponseEntity.ok("쿠키 삭제 완료");
    }

}
