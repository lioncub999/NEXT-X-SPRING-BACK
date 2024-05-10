package org.example.apitest.Mapper.auth;

import org.apache.ibatis.annotations.Mapper;
import org.example.apitest.model.auth.AuthInfo;
import org.example.apitest.model.auth.Login;
import org.example.apitest.model.auth.Register;
import org.example.apitest.model.user.UserInfo;

@Mapper
public interface AuthMapper {

    UserInfo selectUserByNm(String loginNm);

    int dupCheck(Register register);

    void register(Register register);
}
