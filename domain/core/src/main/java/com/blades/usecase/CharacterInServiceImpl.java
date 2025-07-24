package com.blades.usecase;

import com.blades.model.character.Character;
import com.blades.model.character.CharacterBackground;
import com.blades.model.character.CharacterHeritage;
import com.blades.model.character.CharacterType;
import com.blades.model.character.CharacterVice;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.update.UpdateCharacterRequest;
import com.blades.port.in.CharacterInService;
import com.blades.port.out.CharacterOutService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CharacterInServiceImpl implements CharacterInService {

    private final CharacterOutService characterOutService;

    @Override
    public void createCharacter(CreateCharacterRequest character) {
        characterOutService.saveCharacter(Character.builder()
                                              .id(UUID.randomUUID())
                                              .owningUserId(character.owningUserId())
                                              .name(character.name())
                                              .build());
    }

    @Override
    public void updateCharacter(UpdateCharacterRequest updateCharacterRequest) {
        Character currentCharacter = characterOutService.getCharacter(updateCharacterRequest.userId(),
                                                                      updateCharacterRequest.id());

        Character.CharacterBuilder characterBuilder = currentCharacter.toBuilder();

        switch (updateCharacterRequest.characterPartRequest()) {
            case NAME -> characterBuilder
                .name(updateCharacterRequest.characterUpdateElement().getString());
            case ALIAS -> characterBuilder
                .alias(updateCharacterRequest.characterUpdateElement().getString());
            case TYPE -> characterBuilder
                .type(CharacterType.valueOf(updateCharacterRequest.characterUpdateElement().getString()));
            case CREW_NAME -> characterBuilder
                .crewId(updateCharacterRequest.characterUpdateElement().getUUID());
            case LOOK -> characterBuilder
                .look(updateCharacterRequest.characterUpdateElement().getString());
            case HERITAGE -> characterBuilder
                .heritage(CharacterHeritage.valueOf(updateCharacterRequest.characterUpdateElement().getString()));
            case BACKGROUND -> characterBuilder
                .background(CharacterBackground.valueOf(updateCharacterRequest.characterUpdateElement().getString()));
            case BACKGROUND_DETAILS -> characterBuilder
                .backgroundDetails(updateCharacterRequest.characterUpdateElement().getString());
            case VICE -> characterBuilder
                .vice(CharacterVice.valueOf(updateCharacterRequest.characterUpdateElement().getString()));
            case VICE_DETAILS -> characterBuilder
                .viceDetails(updateCharacterRequest.characterUpdateElement().getString());
        }

        characterOutService.saveCharacter(characterBuilder.build());
    }

    @Override
    public List<Character> getCharacters(UUID userId) {
        return characterOutService.getCharacters(userId);
    }

    @Override
    public List<Character> getAllCharacters() {
        return characterOutService.getAllCharacters();
    }

    @Override
    public Character getCharacter(UUID userId, UUID id) {
        return characterOutService.getCharacter(userId, id);
    }

    @Override
    public void deleteCharacter(UUID userId, UUID id) {
        characterOutService.deleteCharacter(userId, id);
    }

    @Override
    public String getCharacterName(UUID id) {
        return characterOutService.getCharacter(id).name();
    }

    @Override
    public void removeCrew(UUID crewId) {
        characterOutService.getAllCharacters().stream()
            .filter(character -> character.crewId().map(crewId::equals).orElse(false))
            .map(character -> character.toBuilder().crewId(null).build())
            .forEach(characterOutService::saveCharacter);
    }

}
