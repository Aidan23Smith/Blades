package com.blades.usecase;

import com.blades.model.requests.CharacterBackgroundRequest;
import com.blades.model.requests.CharacterHeritageRequest;
import com.blades.model.requests.CharacterTypeRequest;
import com.blades.model.requests.CharacterViceRequest;
import com.blades.model.requests.SaveCharacterRequest;
import com.blades.model.response.CharacterResponse;
import com.blades.model.requests.CreateCharacterRequest;
import com.blades.model.requests.UpdateCharacterRequest;
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
    public CharacterResponse getCharacter(UUID userId, UUID id) {
        return characterOutService.getCharacter(userId, id);
    }

    @Override
    public void deleteCharacter(UUID userId, UUID id) {
        characterOutService.deleteCharacter(userId, id);
    }
}
