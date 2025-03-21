package com.blades.port.in;

import com.blades.model.response.CharacterResponse;
import com.blades.model.requests.CreateCharacterRequest;
import com.blades.model.requests.UpdateCharacterRequest;

import java.util.List;
import java.util.UUID;

public interface CharacterInService {

  void createCharacter(CreateCharacterRequest character);

  void updateCharacter(UpdateCharacterRequest updateCharacterRequest);

  List<CharacterResponse> getCharacters(UUID userId);

  CharacterResponse getCharacter(UUID userId, UUID id);

  void deleteCharacter(UUID userId, UUID id);

}
