package com.blades.service;

import com.blades.converter.LoginConverter;
import com.blades.dao.LoginDao;
import com.blades.model.CustomUser;
import com.blades.model.requests.LoginUserRequest;
import com.blades.port.out.LoginOutService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LoginOutServiceImpl implements LoginOutService {

    private final LoginConverter loginConverter;
    private final LoginDao loginDao;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginConverter.toUser(loginDao.findByUsername(username));
    }

    @Override
    public void createUser(LoginUserRequest user) {
        loginDao.save(loginConverter.toUserPO(user));
    }

}
