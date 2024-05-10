package org.example.apitest.controller.auth;

import org.example.apitest.model.auth.AuthInfo;
import org.example.apitest.model.auth.Login;
import org.example.apitest.model.auth.Register;
import org.example.apitest.model.log.TraceWriter;
import org.example.apitest.service.auth.CustomUserDetailsService;
import org.example.apitest.service.auth.AuthService;
import org.example.apitest.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager; // authenticate 메서드 : username, password 기반 인증 수행

    public AuthController(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    //TODO: 회원가입 중복확인
    @ResponseBody
    @PostMapping("/dupCheck")
    public Boolean dupCheck(
            HttpServletRequest request,
            @RequestBody Register register
            ) {
        TraceWriter traceWriter = new TraceWriter("", request.getMethod(), request.getServletPath());
        traceWriter.add("< INPUT >");
        traceWriter.add("[register.registerNm : " + register.getRegisterNm() + "]");
        traceWriter.add("");

        boolean result = false;

        try {
            int count = authService.dupCheck(register);
            if (count == 0) {
                result = true;
            }
        } catch (Exception e) {
            traceWriter.add("ERROR MESSAGE :" + e);
        }

        traceWriter.add("< OUTPUT >");
        traceWriter.add("[result : " + result + "]");
        traceWriter.log(0);

        return result;
    }

    //TODO: 회원가입
    @ResponseBody
    @PostMapping("/register")
    public Boolean register(
            HttpServletRequest request,
            @RequestBody Register register
    ) {
        TraceWriter traceWriter = new TraceWriter("", request.getMethod(), request.getServletPath());
        traceWriter.add("< INPUT >");
        traceWriter.add("[register.registerNm : " + register.getRegisterNm() + "]");
        traceWriter.add("");

        boolean result = false;

        try {
            int count = authService.dupCheck(register);
            if (count == 0) {
                String encodedPassword = passwordEncoder.encode(register.getRegisterPw());
                register.setRegisterPw(encodedPassword);

                result = authService.register(register);
            }
        } catch (Exception e) {
            traceWriter.add("ERROR MESSAGE :" + e);
        }

        traceWriter.add("< OUTPUT >");
        traceWriter.add("[result : " + result + "]");
        traceWriter.log(0);

        return result;
    }

    //TODO: 회원가입
    @ResponseBody
    @PostMapping("/login")
    public AuthInfo login(
            HttpServletRequest request,
            @RequestBody Login login
    ) {
        TraceWriter traceWriter = new TraceWriter("", request.getMethod(), request.getServletPath());
        traceWriter.add("< INPUT >");
        traceWriter.add("[register.registerNm : " + login.getLoginNm() + "]");
        traceWriter.add("");

        AuthInfo authInfo = new AuthInfo();

        try {
            // username, password 인증 시도
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getLoginNm(), login.getLoginPw()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("로그인 실패");
        }
//
//        // 인증 성공 후 인증된 user의 정보를 갖고옴
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getLoginNm());
//        // subject, claim 모두 UserDetails를 사용하므로 객체를 그대로 전달
        String token = jwtUtil.generateToken(userDetails);
        authInfo.setToken(token);

        traceWriter.add("< OUTPUT >");
        traceWriter.add("[token : " + "aa" + "]");
        traceWriter.log(0);

        // 생성된 토큰을 응답 (Test)
        return authInfo;
    }
}
