package com.blades.model.requests.crew;

import java.util.List;
import java.util.UUID;

public record UpdateCrewCharactersRequest(UUID crewId,
                                          List<UUID> allCharacterIds) {

}
