package com.blades.converter;

import com.blades.data.crew.CrewDto;
import com.blades.model.requests.crew.CreateCrewRequest;

import org.springframework.stereotype.Service;

@Service
public class RequestCrewConverter {

    public CreateCrewRequest toCrewRequest(CrewDto crew) {
        return new CreateCrewRequest(crew.crewName());
    }

}
