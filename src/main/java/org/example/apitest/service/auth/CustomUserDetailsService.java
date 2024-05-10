package org.example.apitest.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.apitest.Mapper.auth.AuthMapper;
import org.example.apitest.model.auth.AuthInfo;
import org.example.apitest.model.auth.Login;
import org.example.apitest.model.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AuthMapper authMapper;

    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String loginNm) throws UsernameNotFoundException {

        UserInfo userinfo = new UserInfo();
        userinfo = authMapper.selectUserByNm(loginNm);

        return new User(loginNm, userinfo.getUserPw(), AuthorityUtils.createAuthorityList());
    }
}
