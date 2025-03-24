package com.blades.service;

import com.blades.converter.CrewConverter;
import com.blades.dao.CrewDao;
import com.blades.model.requests.crew.SaveCrewRequest;
import com.blades.model.response.crew.CrewResponse;
import com.blades.port.out.CrewOutService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CrewOutServiceImpl implements CrewOutService {

    private final CrewDao crewDao;
    private final CrewConverter characterConverter;

    @Override
    public void saveCrew(SaveCrewRequest saveCrewRequest) {
        crewDao.save(characterConverter.toCrewPO(saveCrewRequest));
    }

    @Override
    public List<CrewResponse> getCrews() {
        return characterConverter.toCrewResponses(crewDao.findAll());
    }

    @Override
    public CrewResponse getCrew(UUID crewId) {
        return characterConverter.toCrewResponse(crewDao.findByCrewId(crewId));
    }

    @Override
    public void deleteCrew(UUID userId) {
        crewDao.deleteByCrewId(userId);
    }
}
