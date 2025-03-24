package com.blades.usecase;

import com.blades.model.requests.crew.CreateCrewRequest;
import com.blades.model.requests.crew.SaveCrewRequest;
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
            case CHARACTER_IDS -> crewBuilder
                .characterIds(List.of(UUID.fromString(updateCrewRequest.changeElement())));
            case LAIR -> crewBuilder
                .lair(updateCrewRequest.changeElement());
            case LAIR_DETAILS -> crewBuilder
                .lairDetails(updateCrewRequest.changeElement());
        }

        crewOutService.saveCrew(crewBuilder.build());
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
}
