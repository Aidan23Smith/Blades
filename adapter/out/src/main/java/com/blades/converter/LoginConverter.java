package com.blades.converter;

import com.blades.data.login.UserPO;
import com.blades.model.CustomUser;
import com.blades.model.requests.LoginUserRequest;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginConverter {

    public UserPO toUserPO(LoginUserRequest user) {
        return new UserPO(UUID.randomUUID(), user.username(), user.password());
    }

    public CustomUser toUser(UserPO user) {
        return new CustomUser(User
                                  .withDefaultPasswordEncoder() //todo create new encoder
                                  .username(user.getUsername())
                                  .password(user.getPassword())
                                  .roles("USER")
                                  .build(),
                              user.getUserId());
    }

}
