package com.blades.data.login;

import org.springframework.data.annotation.Id;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPO {

    @Id
    UUID userId;
    String username;
    String password;

}
