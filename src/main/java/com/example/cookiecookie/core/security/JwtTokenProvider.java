package com.example.cookiecookie.core.security;

import com.example.cookiecookie.core.error.ErrorCode;
import com.example.cookiecookie.core.error.exception.UnAuthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final CustomUserDetailService customUserDetailService;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessTokenExpiration}")
    private long accessTokenValidTime;

    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenValidTime;


    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());

        } catch (Exception e) {
            throw new UnAuthorizedException("만료된 토큰입니다", ErrorCode.UNAUTHORIZED_EXCEPTION);
        }
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getLoginId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getLoginId(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();

        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }

    public String createAccessToken(String loginId) {
        return this.createToken(loginId, accessTokenValidTime);
    }

    public String createRefreshToken(String loginId) {
        return this.createToken(loginId, refreshTokenValidTime);
    }

    private String createToken(String loginId, long tokenValid) {
        Claims claims = Jwts.claims().setSubject(loginId);
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenValid))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "bearer "+ accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("RefreshToken", "bearer "+ refreshToken);
    }

    public String resolveAccessToken(HttpServletRequest request) {
        if (request.getHeader("Authorization") != null ) {
            return request.getHeader("Authorization").substring(7);
        }

        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        if (request.getHeader("refreshToken") != null ) {
            return request.getHeader("refreshToken").substring(7);
        }

        return null;
    }

}
