package com.blades.port.in;

import com.blades.model.response.character.CharacterResponse;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.update.UpdateCharacterRequest;

import java.util.List;
import java.util.UUID;

public interface CharacterInService {

  void createCharacter(CreateCharacterRequest character);

  void updateCharacter(UpdateCharacterRequest updateCharacterRequest);

  List<CharacterResponse> getCharacters(UUID userId);

  List<CharacterResponse> getAllCharacters();

  CharacterResponse getCharacter(UUID userId, UUID id);

  void deleteCharacter(UUID userId, UUID id);

  String getCharacterName(UUID id);

  void removeCrew(UUID crewId);

}
