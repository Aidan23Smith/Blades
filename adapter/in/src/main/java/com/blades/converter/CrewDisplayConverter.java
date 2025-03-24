package com.blades.converter;

import com.blades.data.crew.CrewDto;
import com.blades.model.response.crew.CrewResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewDisplayConverter {

    public List<CrewDto> toCrewDtos(List<CrewResponse> crews) {
        return crews.stream().map(this::toCrewDto).toList();
    }

    public CrewDto toCrewDto(CrewResponse crew) {
        return new CrewDto(crew.crewId(),
                                crew.crewName(),
                                crew.characterIds(),
                                crew.lair().orElse(null),
                                crew.lairDetails().orElse(null));
    }

}
