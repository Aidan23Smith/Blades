package com.blades.data.crew;

import java.util.List;
import java.util.UUID;

public record CrewDto(UUID crewId,
                      String crewName,
                      List<String> characterNames,
                      String lair,
                      String lairDetails) {

}
