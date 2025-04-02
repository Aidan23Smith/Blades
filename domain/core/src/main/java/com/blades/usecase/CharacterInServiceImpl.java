package com.blades.usecase;

import com.blades.model.requests.character.CharacterBackgroundRequest;
import com.blades.model.requests.character.CharacterHeritageRequest;
import com.blades.model.requests.character.CharacterTypeRequest;
import com.blades.model.requests.character.CharacterViceRequest;
import com.blades.model.requests.character.SaveCharacterRequest;
import com.blades.model.response.character.CharacterResponse;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.update.UpdateCharacterRequest;
import com.blades.port.in.CharacterInService;
import com.blades.port.out.CharacterOutService;
import com.blades.usecase.converter.SaveCharacterConverter;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CharacterInServiceImpl implements CharacterInService {

    private final CharacterOutService characterOutService;
    private final SaveCharacterConverter characterConverter;

    @Override
    public void createCharacter(CreateCharacterRequest character) {
        characterOutService.saveCharacter(characterConverter.toSaveCharacterRequest(character));
    }

    @Override
    public void updateCharacter(UpdateCharacterRequest updateCharacterRequest) {
        CharacterResponse currentCharacter = characterOutService.getCharacter(updateCharacterRequest.userId(),
                                                                              updateCharacterRequest.id());

        SaveCharacterRequest.SaveCharacterRequestBuilder characterBuilder = characterConverter
            .toSaveCharacterRequest(currentCharacter)
            .toBuilder();

        switch (updateCharacterRequest.characterPartRequest()) {
            case NAME -> characterBuilder
                .name(updateCharacterRequest.characterUpdateElement().getString());
            case ALIAS -> characterBuilder
                .alias(updateCharacterRequest.characterUpdateElement().getString());
            case TYPE -> characterBuilder
                .type(CharacterTypeRequest.valueOf(updateCharacterRequest.characterUpdateElement().getString()));
            case CREW_NAME -> characterBuilder
                .crewId(updateCharacterRequest.characterUpdateElement().getUUID());
            case LOOK -> characterBuilder
                .look(updateCharacterRequest.characterUpdateElement().getString());
            case HERITAGE -> characterBuilder
                .heritage(CharacterHeritageRequest.valueOf(updateCharacterRequest.characterUpdateElement().getString()));
            case BACKGROUND -> characterBuilder
                .background(CharacterBackgroundRequest.valueOf(updateCharacterRequest.characterUpdateElement().getString()));
            case BACKGROUND_DETAILS -> characterBuilder
                .backgroundDetails(updateCharacterRequest.characterUpdateElement().getString());
            case VICE -> characterBuilder
                .vice(CharacterViceRequest.valueOf(updateCharacterRequest.characterUpdateElement().getString()));
            case VICE_DETAILS -> characterBuilder
                .viceDetails(updateCharacterRequest.characterUpdateElement().getString());
        }

        characterOutService.saveCharacter(characterBuilder.build());
    }

    @Override
    public List<CharacterResponse> getCharacters(UUID userId) {
        return characterOutService.getCharacters(userId);
    }

    @Override
    public List<CharacterResponse> getAllCharacters() {
        return characterOutService.getAllCharacters();
    }

    @Override
    public CharacterResponse getCharacter(UUID userId, UUID id) {
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
            .map(characterConverter::toSaveCharacterRequest)
            .forEach(characterOutService::saveCharacter);
    }

}
