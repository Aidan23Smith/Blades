package com.blades.model.requests.crew;

import java.util.UUID;

public record UpdateCrewRequest(UUID crewId,
                                CrewPartRequest crewPartRequest,
                                String changeElement) {

}
