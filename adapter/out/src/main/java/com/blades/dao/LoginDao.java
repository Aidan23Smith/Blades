package com.blades.dao;

import com.blades.data.login.UserPO;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginDao extends MongoRepository<UserPO, String> {

    UserPO findByUsername(String username);

}
