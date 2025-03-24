package com.blades.port.out;

import com.blades.model.requests.character.SaveCharacterRequest;
import com.blades.model.response.character.CharacterResponse;

import java.util.List;
import java.util.UUID;

public interface CharacterOutService {

    void saveCharacter(SaveCharacterRequest saveCharacterRequest);

    List<CharacterResponse> getCharacters(UUID userId);

    CharacterResponse getCharacter(UUID userId, UUID id);

    void deleteCharacter(UUID userId, UUID id);

}
