package com.blades.data.crew;

import java.util.List;
import java.util.UUID;

public record CrewDto(UUID crewId,
                      String crewName,
                      List<UUID> characterIds,
                      String lair,
                      String lairDetails) {

}
