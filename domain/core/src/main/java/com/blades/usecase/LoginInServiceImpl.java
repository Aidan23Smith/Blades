package com.blades.usecase;

import com.blades.model.requests.LoginUserRequest;
import com.blades.port.in.LoginInService;
import com.blades.port.out.LoginOutService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginInServiceImpl implements LoginInService {

    private final LoginOutService loginOutService;

    @Override
    public void createUser(LoginUserRequest user) {
        loginOutService.createUser(user);
    }
}
