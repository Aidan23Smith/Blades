package com.blades.model.requests.crew.update;

import com.blades.model.requests.crew.CrewPartRequest;
import com.blades.model.requests.crew.update.elements.CrewUpdateElement;

import java.util.UUID;

public record UpdateCrewRequest(UUID crewId,
                                CrewPartRequest crewPartRequest,
                                CrewUpdateElement crewUpdateElement) {

}
