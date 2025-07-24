package com.blades.usecase;

import com.blades.model.requests.crew.CreateCrewRequest;
import com.blades.model.requests.crew.update.UpdateCrewRequest;
import com.blades.model.crew.Crew;
import com.blades.port.in.CrewInService;
import com.blades.port.out.CrewOutService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CrewInServiceImpl implements CrewInService {

    private final CrewOutService crewOutService;

    @Override
    public void createCrew(CreateCrewRequest crew) {
        crewOutService.saveCrew(Crew.builder()
                                    .crewId(UUID.randomUUID())
                                    .crewName(crew.crewName())
                                    .build());
    }

    @Override
    public void updateCrew(UpdateCrewRequest updateCrewRequest) {
        Crew currentCrew = crewOutService.getCrew(updateCrewRequest.crewId());

        Crew.CrewBuilder updatedCrew = currentCrew
            .toBuilder();

        switch (updateCrewRequest.crewPartRequest()) {
            case CREW_NAME -> updatedCrew
                .crewName(updateCrewRequest.crewUpdateElement().getString());
            case LAIR -> updatedCrew
                .lair(updateCrewRequest.crewUpdateElement().getString());
            case LAIR_DETAILS -> updatedCrew
                .lairDetails(updateCrewRequest.crewUpdateElement().getString());
            case CHARACTER_IDS -> updatedCrew
                .characterIds(updateCrewRequest.crewUpdateElement().getUUIDList());
        }

        crewOutService.saveCrew(updatedCrew.build());
    }

    @Override
    public List<Crew> getCrews() {
        return crewOutService.getCrews();
    }

    @Override
    public Crew getCrew(UUID crewId) {
        return crewOutService.getCrew(crewId);
    }

    @Override
    public void deleteCrew(UUID crewId) {
        crewOutService.deleteCrew(crewId);
    }

    @Override
    public void removeCharacter(UUID characterId) {
        crewOutService.getCrews().stream()
            .filter(crew -> crew.characterIds().contains(characterId))
            .forEach(crew -> {
                List<UUID> updatedCharacterIds = crew.characterIds().stream()
                    .filter(id -> !id.equals(characterId))
                    .toList();

                Crew updatedCrew = crew.toBuilder()
                    .characterIds(updatedCharacterIds)
                    .build();

                crewOutService.saveCrew(updatedCrew);
            });
    }

    @Override
    public String getCrewName(UUID crewId) {
        return getCrew(crewId).crewName();
    }

}
