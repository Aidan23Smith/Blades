package com.blades.model;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CustomUser extends User {

    private final UUID userID;

    public CustomUser(UserDetails user, UUID uuid) {
        super(user.getUsername(),
              user.getPassword(),
              user.isEnabled(),
              user.isAccountNonExpired(),
              user.isCredentialsNonExpired(),
              user.isAccountNonLocked(),
              user.getAuthorities());
        userID = uuid;
    }
}
