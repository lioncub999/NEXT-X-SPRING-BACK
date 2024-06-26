package org.example.apitest.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.apitest.service.auth.CustomUserDetailsService;
import org.example.apitest.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HomeController {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager; // authenticate 메서드 : username, password 기반 인증 수행

    @GetMapping("/home")
    public int home(
            Authentication authentication
    ) {
        System.out.println(authentication);
        return 1;
    }

    @PostMapping("/auth")
    public ResponseEntity<LoginSuccessResponse> authenticateTest(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 @RequestBody LoginRequest loginRequest) {
        log.info("/auth 호출");
        try {
            // username, password 인증 시도
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("로그인 실패");
        }
        // 인증 성공 후 인증된 user의 정보를 갖고옴
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username);
        // subject, claim 모두 UserDetails를 사용하므로 객체를 그대로 전달
        String token = jwtUtil.generateToken(userDetails);

        // 생성된 토큰을 응답 (Test)
        return ResponseEntity.ok(new LoginSuccessResponse(token));
    }
    // 인증요청 객체
    @AllArgsConstructor
    @Data
    @Getter
    @Setter
    static class LoginRequest{
        private String username;
        private String password;
    }
    // 인증요청에 대한 응답 객체
    @AllArgsConstructor
    @Data
    @Getter
    @Setter
    static class LoginSuccessResponse {
        private String token;
    }
}