package org.example.apitest.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apitest.service.auth.CustomUserDetailsService;
import org.example.apitest.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization"); // 헤더 파싱
        String loginNm = "", token = "";

        if (authorization != null && authorization.startsWith("Bearer ")) { // Bearer 토큰 파싱
            log.info(token);

            token = authorization.substring(7); // jwt token 파싱
            log.info(token);

            loginNm = jwtUtil.getUsernameFromToken(token); // username 얻어오기
        } else {
            filterChain.doFilter(request, response);
            return;
            }
        // 현재 SecurityContextHolder 에 인증객체가 있는지 확인
        if (loginNm != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginNm);
            // 토큰 유효여부 확인
            log.info("JWT Filter token = {}", token);
            log.info("JWT Filter userDetails = {}", userDetails.getUsername());
            if (jwtUtil.isValidToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}