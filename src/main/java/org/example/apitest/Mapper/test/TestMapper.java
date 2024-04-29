package org.example.apitest.Mapper.test;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    int userCount();
}
