package com.blades.usecase;

import com.blades.model.requests.character.CharacterBackgroundRequest;
import com.blades.model.requests.character.CharacterHeritageRequest;
import com.blades.model.requests.character.CharacterTypeRequest;
import com.blades.model.requests.character.CharacterViceRequest;
import com.blades.model.requests.character.SaveCharacterRequest;
import com.blades.model.response.character.CharacterResponse;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.UpdateCharacterRequest;
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
                .name(updateCharacterRequest.changeElement());
            case ALIAS -> characterBuilder
                .alias(updateCharacterRequest.changeElement());
            case TYPE -> characterBuilder
                .type(CharacterTypeRequest.valueOf(updateCharacterRequest.changeElement()));
            case CREW_NAME -> characterBuilder
                .crewName(updateCharacterRequest.changeElement());
            case LOOK -> characterBuilder
                .look(updateCharacterRequest.changeElement());
            case HERITAGE -> characterBuilder
                .heritage(CharacterHeritageRequest.valueOf(updateCharacterRequest.changeElement()));
            case BACKGROUND -> characterBuilder
                .background(CharacterBackgroundRequest.valueOf(updateCharacterRequest.changeElement()));
            case BACKGROUND_DETAILS -> characterBuilder
                .backgroundDetails(updateCharacterRequest.changeElement());
            case VICE -> characterBuilder
                .vice(CharacterViceRequest.valueOf(updateCharacterRequest.changeElement()));
            case VICE_DETAILS -> characterBuilder
                .viceDetails(updateCharacterRequest.changeElement());
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
}
