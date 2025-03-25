package com.blades.usecase;

import com.blades.model.requests.crew.CreateCrewRequest;
import com.blades.model.requests.crew.SaveCrewRequest;
import com.blades.model.requests.crew.UpdateCrewCharactersRequest;
import com.blades.model.requests.crew.UpdateCrewRequest;
import com.blades.model.response.crew.CrewResponse;
import com.blades.port.in.CrewInService;
import com.blades.port.out.CrewOutService;
import com.blades.usecase.converter.SaveCrewConverter;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CrewInServiceImpl implements CrewInService {

    private final CrewOutService crewOutService;
    private final SaveCrewConverter crewConverter;

    @Override
    public void createCrew(CreateCrewRequest crew) {
        crewOutService.saveCrew(crewConverter.toSaveCrewRequest(crew));
    }

    @Override
    public void updateCrew(UpdateCrewRequest updateCrewRequest) {
        CrewResponse currentCrew = crewOutService.getCrew(updateCrewRequest.crewId());

        SaveCrewRequest.SaveCrewRequestBuilder crewBuilder = crewConverter
            .toSaveCrewRequest(currentCrew)
            .toBuilder();

        switch (updateCrewRequest.crewPartRequest()) {
            case CREW_NAME -> crewBuilder
                .crewName(updateCrewRequest.changeElement());
            case LAIR -> crewBuilder
                .lair(updateCrewRequest.changeElement());
            case LAIR_DETAILS -> crewBuilder
                .lairDetails(updateCrewRequest.changeElement());
        }

        crewOutService.saveCrew(crewBuilder.build());
    }

    @Override
    public void updateCrewCharacters(UpdateCrewCharactersRequest updateCrewCharactersRequest) {
        CrewResponse currentCrew = crewOutService.getCrew(updateCrewCharactersRequest.crewId());

        saveCrewWithCharacters(currentCrew, updateCrewCharactersRequest.allCharacterIds());
    }

    @Override
    public List<CrewResponse> getCrews() {
        return crewOutService.getCrews();
    }

    @Override
    public CrewResponse getCrew(UUID crewId) {
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

                saveCrewWithCharacters(crew, updatedCharacterIds);
            });
    }

    private void saveCrewWithCharacters(CrewResponse crewResponse, List<UUID> characterIds) {
        CrewResponse updatedCrew = crewResponse.toBuilder()
            .characterIds(characterIds)
            .build();

        crewOutService.saveCrew(crewConverter.toSaveCrewRequest(updatedCrew));
    }

}
