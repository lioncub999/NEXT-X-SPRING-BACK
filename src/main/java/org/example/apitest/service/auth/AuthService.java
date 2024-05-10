package org.example.apitest.service.auth;

import org.example.apitest.Mapper.auth.AuthMapper;
import org.example.apitest.model.auth.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    AuthMapper authMapper;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int dupCheck(Register register) {
        return authMapper.dupCheck(register);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean register(Register register) {
        authMapper.register(register);
        return true;
    }
}
