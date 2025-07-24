package com.blades.usecase;

import com.blades.model.character.Character;
import com.blades.model.character.Trauma;
import com.blades.port.in.StressService;
import com.blades.port.out.CharacterOutService;

import org.springframework.stereotype.Service;

import java.util.UUID;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StressServiceImpl implements StressService {

    private final CharacterOutService characterOutService;

    @Override
    public void increaseStress(UUID userId, UUID id) {
        Character currentCharacter = characterOutService.getCharacter(userId, id);

        characterOutService.saveCharacter(currentCharacter
                                              .toBuilder()
                                              .stress(currentCharacter.stress() + 1)
                                              .build());
    }

    @Override
    public void decreaseStress(UUID userId, UUID id) {
        Character currentCharacter = characterOutService.getCharacter(userId, id);

        if (currentCharacter.stress() == 0) {
            return;
        }

        characterOutService.saveCharacter(currentCharacter
                                              .toBuilder()
                                              .stress(currentCharacter.stress() - 1)
                                              .build());
    }

    @Override
    public boolean isTooStressed(UUID userId, UUID id) {
        Character currentCharacter = characterOutService.getCharacter(userId, id);

        return currentCharacter.stress() >= 9;
    }

    @Override
    public void setNewTrauma(UUID userId, UUID id, Trauma trauma) {
        Character currentCharacter = characterOutService.getCharacter(userId, id);

        characterOutService.saveCharacter(currentCharacter
                                              .toBuilder()
                                              .stress(0)
                                              .trauma(trauma)
                                              .build());
    }

}
