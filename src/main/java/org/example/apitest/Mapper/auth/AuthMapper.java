package org.example.apitest.Mapper.auth;

import org.apache.ibatis.annotations.Mapper;
import org.example.apitest.model.auth.Register;

@Mapper
public interface AuthMapper {

    int dupCheck(Register register);

    void register(Register register);
}
