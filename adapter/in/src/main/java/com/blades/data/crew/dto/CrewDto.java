package com.blades.data.crew.dto;

import java.util.List;
import java.util.UUID;

public record CrewDto(UUID crewId,
                      String crewName,
                      List<String> characterNames,
                      String lair,
                      String lairDetails) {

    public boolean hasCrewMembers() {
        return !characterNames.isEmpty();
    }

}
