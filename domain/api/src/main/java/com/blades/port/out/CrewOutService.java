package com.blades.port.out;

import com.blades.model.requests.crew.SaveCrewRequest;
import com.blades.model.response.crew.CrewResponse;

import java.util.List;
import java.util.UUID;

public interface CrewOutService {

    void saveCrew(SaveCrewRequest saveCrewRequest);

    List<CrewResponse> getCrews();

    CrewResponse getCrew(UUID crewId);

    void deleteCrew(UUID crewId);

}
