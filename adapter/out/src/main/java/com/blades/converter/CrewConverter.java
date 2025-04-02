package com.blades.converter;

import com.blades.data.crew.CrewPO;
import com.blades.model.requests.crew.SaveCrewRequest;
import com.blades.model.response.crew.CrewResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewConverter {

    public List<CrewResponse> toCrewResponses(List<CrewPO> crews) {
        return crews.stream()
            .map(this::toCrewResponse)
            .toList();
    }

    public CrewResponse toCrewResponse(CrewPO crew) {
        return new CrewResponse(crew.crewId(),
                                crew.crewName(),
                                crew.characterIds(),
                                crew.lair().orElse(null),
                                crew.lairDetails().orElse(null));
    }

    public CrewPO toCrewPO(SaveCrewRequest crew) {
        return new CrewPO(crew.crewId(),
                          crew.crewName(),
                          crew.characterIds(),
                          crew.lair().orElse(null),
                          crew.lairDetails().orElse(null));
    }

}
