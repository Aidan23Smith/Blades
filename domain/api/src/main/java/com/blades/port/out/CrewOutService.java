package com.blades.port.out;

import com.blades.model.crew.Crew;

import java.util.List;
import java.util.UUID;

public interface CrewOutService {

    void saveCrew(Crew crew);

    List<Crew> getCrews();

    Crew getCrew(UUID crewId);

    void deleteCrew(UUID crewId);

}
