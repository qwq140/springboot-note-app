package org.example.noteappserver.global.security;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.noteappserver.global.exception.Exception403;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


// JWT를 통한 인증 필터
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);

        if(StringUtils.hasText(jwt)) {
           if (!jwtProvider.validateToken(jwt)){
               throw new Exception403("접근이 거부되었습니다.");
           }

           try {
               Authentication authentication = jwtProvider.getAuthentication(jwt);
               SecurityContextHolder.getContext().setAuthentication(authentication);
           } catch (TokenExpiredException | SignatureVerificationException exception) {
               exception.printStackTrace();
           }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtVO.HEADER); // 헤더에서 토큰 가져오기

        if(bearerToken != null && bearerToken.startsWith(JwtVO.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtVO.TOKEN_PREFIX.length());
        }
        return null;
    }
}
