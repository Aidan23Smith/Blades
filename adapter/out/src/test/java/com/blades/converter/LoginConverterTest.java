package com.blades.converter;

import com.blades.data.login.UserPO;
import com.blades.model.CustomUser;
import com.blades.model.requests.LoginUserRequest;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginConverterTest {

    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final UUID USER_ID = UUID.randomUUID();
    private final LoginConverter loginConverter = new LoginConverter();

    @Test
    void toUserPO() {
        UserPO expected = new UserPO(USER_ID, USERNAME, PASSWORD);

        try (MockedStatic<UUID> uuid = Mockito.mockStatic(UUID.class)) {
            uuid.when(UUID::randomUUID).thenReturn(USER_ID);

            UserPO actual = loginConverter.toUserPO(new LoginUserRequest(USERNAME, PASSWORD));

            assertEquals(expected, actual);
        }
    }

    @Test
    void toUser() {
        CustomUser expected = new CustomUser(User
                                                 .withDefaultPasswordEncoder()
                                                 .username(USERNAME)
                                                 .password(PASSWORD)
                                                 .roles("USER")
                                                 .build(),
                                             USER_ID);

        CustomUser actual = loginConverter.toUser(new UserPO(USER_ID,
                                                             USERNAME,
                                                             PASSWORD));

        assertEquals(expected, actual);
    }

}
