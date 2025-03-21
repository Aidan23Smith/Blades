package com.blades.usecase;

import com.blades.model.requests.LoginUserRequest;
import com.blades.port.out.LoginOutService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginInServiceImplTest {

    @Mock
    private LoginOutService loginOutService;
    @InjectMocks
    private LoginInServiceImpl underTest;

    @Test
    void createUser() {
        LoginUserRequest request = mock(LoginUserRequest.class);

        underTest.createUser(request);

        verify(loginOutService).createUser(request);
    }

}
