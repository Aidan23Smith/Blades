package com.blades.usecase;

import com.blades.model.requests.character.TraumaRequest;
import com.blades.model.response.character.CharacterResponse;
import com.blades.port.in.StressService;
import com.blades.port.out.CharacterOutService;
import com.blades.usecase.converter.SaveCharacterConverter;

import org.springframework.stereotype.Service;

import java.util.UUID;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StressServiceImpl implements StressService {

    private final CharacterOutService characterOutService;
    private final SaveCharacterConverter characterConverter;

    @Override
    public void increaseStress(UUID userId, UUID id) {
        CharacterResponse currentCharacter = characterOutService.getCharacter(userId, id);

        characterOutService.saveCharacter(characterConverter
                                              .toSaveCharacterRequest(currentCharacter)
                                              .toBuilder()
                                              .stress(currentCharacter.stress() + 1)
                                              .build());
    }

    @Override
    public void decreaseStress(UUID userId, UUID id) {
        CharacterResponse currentCharacter = characterOutService.getCharacter(userId, id);

        if (currentCharacter.stress() == 0) {
            return;
        }

        characterOutService.saveCharacter(characterConverter
                                              .toSaveCharacterRequest(currentCharacter)
                                              .toBuilder()
                                              .stress(currentCharacter.stress() - 1)
                                              .build());
    }

    @Override
    public boolean isTooStressed(UUID userId, UUID id) {
        CharacterResponse currentCharacter = characterOutService.getCharacter(userId, id);

        return currentCharacter.stress() >= 9;
    }

    @Override
    public void setNewTrauma(UUID userId, UUID id, TraumaRequest traumaRequest) {
        CharacterResponse currentCharacter = characterOutService.getCharacter(userId, id);

        characterOutService.saveCharacter(characterConverter
                                              .toSaveCharacterRequest(currentCharacter)
                                              .toBuilder()
                                              .stress(0)
                                              .trauma(traumaRequest)
                                              .build());
    }

}
