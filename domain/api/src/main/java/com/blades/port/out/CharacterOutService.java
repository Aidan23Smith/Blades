package com.blades.port.out;

import com.blades.model.character.Character;

import java.util.List;
import java.util.UUID;

public interface CharacterOutService {

    void saveCharacter(Character character);

    List<Character> getCharacters(UUID userId);

    List<Character> getAllCharacters();

    Character getCharacter(UUID userId, UUID id);

    Character getCharacter(UUID id);

    void deleteCharacter(UUID userId, UUID id);

}
