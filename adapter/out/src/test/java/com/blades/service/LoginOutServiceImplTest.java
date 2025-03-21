package com.blades.service;

import com.blades.converter.LoginConverter;
import com.blades.dao.LoginDao;
import com.blades.data.login.UserPO;
import com.blades.model.CustomUser;
import com.blades.model.requests.LoginUserRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginOutServiceImplTest {

    private static final String USERNAME = "username";

    @Mock
    private LoginConverter loginConverter;
    @Mock
    private LoginDao loginDao;

    @InjectMocks
    private LoginOutServiceImpl underTest;

    @Test
    void loadUserByUsername() {
        UserPO userPO = mock(UserPO.class);
        CustomUser expected = mock(CustomUser.class);
        when(loginDao.findByUsername(USERNAME)).thenReturn(userPO);
        when(loginConverter.toUser(userPO)).thenReturn(expected);

        CustomUser actual = underTest.loadUserByUsername(USERNAME);

        assertEquals(expected, actual);
    }

    @Test
    void createUser() {
        UserPO expected = mock(UserPO.class);
        LoginUserRequest loginUserRequest = mock(LoginUserRequest.class);
        when(loginConverter.toUserPO(loginUserRequest)).thenReturn(expected);

        underTest.createUser(loginUserRequest);

        verify(loginDao).save(expected);
    }

}
