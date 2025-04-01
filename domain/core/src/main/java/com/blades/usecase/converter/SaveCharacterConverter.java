package com.blades.usecase.converter;

import com.blades.model.requests.character.CharacterBackgroundRequest;
import com.blades.model.requests.character.CharacterHeritageRequest;
import com.blades.model.requests.character.CharacterTypeRequest;
import com.blades.model.requests.character.CharacterViceRequest;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.SaveCharacterRequest;
import com.blades.model.response.character.CharacterResponse;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaveCharacterConverter {

    public SaveCharacterRequest toSaveCharacterRequest(CharacterResponse character) {
        return SaveCharacterRequest.builder()
            .id(character.id())
            .owningUserId(character.owningUserId())
            .name(character.name())
            .alias(character.alias().orElse(null))
            .type(character.type()
                      .map(Enum::name)
                      .map(CharacterTypeRequest::valueOf)
                      .orElse(null))
            .crewId(character.crewId().orElse(null))
            .look(character.look().orElse(null))
            .heritage(character.heritage()
                          .map(Enum::name)
                          .map(CharacterHeritageRequest::valueOf)
                          .orElse(null))
            .background(character.background()
                            .map(Enum::name)
                            .map(CharacterBackgroundRequest::valueOf)
                            .orElse(null))
            .backgroundDetails(character.backgroundDetails().orElse(null))
            .vice(character.vice()
                      .map(Enum::name)
                      .map(CharacterViceRequest::valueOf)
                      .orElse(null))
            .viceDetails(character.viceDetails().orElse(null))
            .build();
    }

    public SaveCharacterRequest toSaveCharacterRequest(CreateCharacterRequest character) {
        return SaveCharacterRequest.builder()
            .id(UUID.randomUUID())
            .owningUserId(character.owningUserId())
            .name(character.name())
            .build();
    }

}
