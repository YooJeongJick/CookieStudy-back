package com.example.cookiecookie.core.security;

import com.example.cookiecookie.core.error.JwtErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        log.info(path);
        if (path.contains("/swagger") || path.contains("/v3/api-docs")
                || path.startsWith("/user")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        JwtErrorCode jwtErrorCode;

        try {
            if (accessToken == null && refreshToken != null) {
                if (path.contains("/refresh"))
                    filterChain.doFilter(request, response);
            } else {
                if (jwtTokenProvider.validateToken(accessToken))
                    this.setAuthentication(accessToken);
            }
        } catch (MalformedJwtException e) {
            jwtErrorCode = JwtErrorCode.INVALID_JWT_TOKEN;
            setResponse(response, jwtErrorCode);
            return;
        } catch (ExpiredJwtException e) {
            jwtErrorCode = JwtErrorCode.JWT_TOKEN_EXPIRED;
            setResponse(response, jwtErrorCode);
            return;
        } catch (UnsupportedJwtException e) {
            jwtErrorCode = JwtErrorCode.UNSUPPORTED_JWT_TOKEN;
            setResponse(response, jwtErrorCode);
            return;
        } catch (IllegalArgumentException e) {
            jwtErrorCode = JwtErrorCode.EMPTY_JWT_CLAIMS;
            setResponse(response, jwtErrorCode);
            return;
        } catch (RuntimeException e) {
            jwtErrorCode = JwtErrorCode.JWT_COMPLEX_ERROR;
            log.error("Unexpected error: {}", e.getMessage(), e);
            setResponse(response, jwtErrorCode);
            return;
        }

        filterChain.doFilter(request, response);

    }

    private void setAuthentication(String accessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void setResponse(HttpServletResponse response, JwtErrorCode jwtErrorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", jwtErrorCode.getCode());
        json.put("message", jwtErrorCode.getMessage());

        response.getWriter().print(json);
        response.getWriter().flush();
    }

}
