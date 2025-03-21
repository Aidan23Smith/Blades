package com.blades.port.in;

import com.blades.model.requests.LoginUserRequest;

public interface LoginInService {

    void createUser(LoginUserRequest user);

}
