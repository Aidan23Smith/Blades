package com.blades.port.out;

import com.blades.model.requests.LoginUserRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginOutService extends UserDetailsService {

    void createUser(LoginUserRequest user);

}
