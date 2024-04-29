package org.example.apitest.service.test;

import org.example.apitest.Mapper.test.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public int userCount() {
        return testMapper.userCount();
    }
}
