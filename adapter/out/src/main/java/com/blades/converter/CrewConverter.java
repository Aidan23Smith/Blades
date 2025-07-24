package com.blades.converter;

import com.blades.data.crew.CrewPO;
import com.blades.model.crew.Crew;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewConverter {

    public List<Crew> toCrewResponses(List<CrewPO> crews) {
        return crews.stream()
            .map(this::toCrewResponse)
            .toList();
    }

    public Crew toCrewResponse(CrewPO crew) {
        return new Crew(crew.crewId(),
                        crew.crewName(),
                        crew.characterIds(),
                        crew.lair().orElse(null),
                        crew.lairDetails().orElse(null));
    }

    public CrewPO toCrewPO(Crew crew) {
        return new CrewPO(crew.crewId(),
                          crew.crewName(),
                          crew.characterIds(),
                          crew.lair().orElse(null),
                          crew.lairDetails().orElse(null));
    }

}
