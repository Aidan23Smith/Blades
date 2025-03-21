package com.blades.converter;

import com.blades.data.UserDto;
import com.blades.model.requests.LoginUserRequest;

import org.springframework.stereotype.Service;

@Service
public class UserInConverter {

    public LoginUserRequest toDomainUser(UserDto user) {
        return new LoginUserRequest(user.username(), user.password());
    }

}
