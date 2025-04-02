package com.blades.port.in;

import com.blades.model.requests.crew.CreateCrewRequest;
import com.blades.model.requests.crew.update.UpdateCrewRequest;
import com.blades.model.response.crew.CrewResponse;

import java.util.List;
import java.util.UUID;

public interface CrewInService {

  void createCrew(CreateCrewRequest crew);

  void updateCrew(UpdateCrewRequest updateCrewRequest);

  List<CrewResponse> getCrews();

  CrewResponse getCrew(UUID crewId);

  void deleteCrew(UUID crewId);

  void removeCharacter(UUID characterId);

  String getCrewName(UUID crewId);

}
