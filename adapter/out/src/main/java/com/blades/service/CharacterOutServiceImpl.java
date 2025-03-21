package com.blades.service;

import com.blades.converter.CharacterConverter;
import com.blades.dao.CharacterDao;
import com.blades.model.requests.SaveCharacterRequest;
import com.blades.model.response.CharacterResponse;
import com.blades.port.out.CharacterOutService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CharacterOutServiceImpl implements CharacterOutService {

    private final CharacterDao characterDao;
    private final CharacterConverter characterConverter;

    @Override
    public void saveCharacter(SaveCharacterRequest saveCharacterRequest) {
        characterDao.save(characterConverter.toCharacterPO(saveCharacterRequest));
    }

    @Override
    public List<CharacterResponse> getCharacters(UUID userId) {
        return characterConverter.toCharacterResponses(characterDao.findByOwningUserId(userId));
    }

    @Override
    public CharacterResponse getCharacter(UUID userId, UUID id) {
        return characterConverter.toCharacterResponse(characterDao.findByOwningUserIdAndId(userId, id));
    }

    @Override
    public void deleteCharacter(UUID userId, UUID id) {
        characterDao.deleteByOwningUserIdAndId(userId, id);
    }
}
