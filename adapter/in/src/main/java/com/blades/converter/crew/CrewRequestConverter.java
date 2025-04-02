package com.blades.converter.crew;

import com.blades.data.crew.dto.CrewDto;
import com.blades.model.requests.crew.CreateCrewRequest;

import org.springframework.stereotype.Service;

@Service
public class CrewRequestConverter {

    public CreateCrewRequest toCrewRequest(CrewDto crew) {
        return new CreateCrewRequest(crew.crewName());
    }

}
