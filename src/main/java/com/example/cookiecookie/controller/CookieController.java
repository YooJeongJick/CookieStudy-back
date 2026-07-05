package com.example.cookiecookie.controller;

import com.example.cookiecookie.dto.CookieDto;
import com.example.cookiecookie.service.CookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cookie")
public class CookieController {

    private final CookieService cookieService;

    @PostMapping()
    public ResponseEntity<String> createCookie(@RequestBody CookieDto cookieDto) {
        cookieService.createCookie(cookieDto);
        return ResponseEntity.ok("쿠키 생성 완료");
    }

    @GetMapping()
    public ResponseEntity<List<CookieDto>> findCookies() {
        List<CookieDto> cookies = cookieService.findCookies();
        return ResponseEntity.ok(cookies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CookieDto> findCookie(@PathVariable Long id) {
        CookieDto cookie = cookieService.findCookie(id);
        return ResponseEntity.ok(cookie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCookie(@PathVariable Long id, @RequestBody CookieDto cookieDto) {
        cookieService.updateCookie(id, cookieDto);
        return ResponseEntity.ok("쿠키 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCookie(@PathVariable Long id) {
        cookieService.deleteCookie(id);
        return ResponseEntity.ok("쿠키 삭제 완료");
    }

}
