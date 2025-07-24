package com.blades.port.in;

import com.blades.model.requests.crew.CreateCrewRequest;
import com.blades.model.requests.crew.update.UpdateCrewRequest;
import com.blades.model.crew.Crew;

import java.util.List;
import java.util.UUID;

public interface CrewInService {

  void createCrew(CreateCrewRequest crew);

  void updateCrew(UpdateCrewRequest updateCrewRequest);

  List<Crew> getCrews();

  Crew getCrew(UUID crewId);

  void deleteCrew(UUID crewId);

  void removeCharacter(UUID characterId);

  String getCrewName(UUID crewId);

}
