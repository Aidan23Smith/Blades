package com.blades.dao;

import com.blades.data.character.CharacterPO;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface CharacterDao extends MongoRepository<CharacterPO, UUID> {

    List<CharacterPO> findByOwningUserId(UUID owningUserId);

    CharacterPO findByOwningUserIdAndId(UUID owningUserId, UUID id);

    void deleteByOwningUserIdAndId(UUID owningUserId, UUID id);

}
