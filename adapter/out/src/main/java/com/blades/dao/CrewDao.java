package com.blades.dao;

import com.blades.data.crew.CrewPO;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CrewDao extends MongoRepository<CrewPO, UUID> {

    CrewPO findByCrewId(UUID crewId);

    void deleteByCrewId(UUID crewId);

}
