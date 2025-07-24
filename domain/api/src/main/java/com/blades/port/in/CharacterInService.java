package com.blades.port.in;

import com.blades.model.character.Character;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.update.UpdateCharacterRequest;

import java.util.List;
import java.util.UUID;

public interface CharacterInService {

  void createCharacter(CreateCharacterRequest character);

  void updateCharacter(UpdateCharacterRequest updateCharacterRequest);

  List<Character> getCharacters(UUID userId);

  List<Character> getAllCharacters();

  Character getCharacter(UUID userId, UUID id);

  void deleteCharacter(UUID userId, UUID id);

  String getCharacterName(UUID id);

  void removeCrew(UUID crewId);

}
