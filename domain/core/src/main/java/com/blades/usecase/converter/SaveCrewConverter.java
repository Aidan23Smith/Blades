package com.blades.usecase.converter;

import com.blades.model.requests.crew.CreateCrewRequest;
import com.blades.model.requests.crew.SaveCrewRequest;
import com.blades.model.response.crew.CrewResponse;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaveCrewConverter {

    public SaveCrewRequest toSaveCrewRequest(CrewResponse crew) {
        return SaveCrewRequest.builder()
            .crewId(crew.crewId())
            .crewName(crew.crewName())
            .characterIds(crew.characterIds())
            .lair(crew.lair().orElse(null))
            .lairDetails(crew.lairDetails().orElse(null))
            .build();
    }

    public SaveCrewRequest toSaveCrewRequest(CreateCrewRequest crew) {
        return SaveCrewRequest.builder()
            .crewId(UUID.randomUUID())
            .crewName(crew.crewName())
            .build();
    }

}
