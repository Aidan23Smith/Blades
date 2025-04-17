package com.blades.usecase;

import com.blades.model.requests.character.ArmourRequest;
import com.blades.model.requests.character.HarmRequest;
import com.blades.model.requests.character.SaveCharacterRequest;
import com.blades.model.requests.character.SaveHarmRequest;
import com.blades.model.response.character.CharacterResponse;
import com.blades.model.response.character.HarmResponse;
import com.blades.port.in.HarmService;
import com.blades.port.out.CharacterOutService;
import com.blades.usecase.converter.SaveCharacterConverter;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HarmServiceImpl implements HarmService {

    private final CharacterOutService characterOutService;
    private final SaveCharacterConverter characterConverter;

    @Override
    public void addHarm(UUID userId, UUID id, HarmRequest harmRequest) {
        CharacterResponse currentCharacter = characterOutService.getCharacter(userId, id);

        SaveHarmRequest newHarm = new SaveHarmRequest(nextHarmLevel(currentCharacter.harms(), harmRequest.level()),
                                                      harmRequest.detail());

        characterOutService.saveCharacter(characterConverter
                                              .toSaveCharacterRequest(currentCharacter)
                                              .toBuilder()
                                              .harm(newHarm)
                                              .build());
    }

    @Override
    public void rollForHealingClock(UUID userId, UUID id, int result) {
        CharacterResponse currentCharacter = characterOutService.getCharacter(userId, id);

        SaveCharacterRequest.SaveCharacterRequestBuilder saveCharacterBuilder = characterConverter
            .toSaveCharacterRequest(currentCharacter)
            .toBuilder();

        int healingClock = currentCharacter.healingClock() + howManySectionsToHeal(result);

        if (healingClock >= 4) {
            healingClock -= 4;
            saveCharacterBuilder.clearHarms();
            saveCharacterBuilder.harms(heal(currentCharacter.harms()));
        }

        characterOutService.saveCharacter(saveCharacterBuilder
                                              .healingClock(healingClock)
                                              .build());
    }

    @Override
    public void updateArmour(UUID userId, UUID id, List<ArmourRequest> armourRequest) {
        CharacterResponse currentCharacter = characterOutService.getCharacter(userId, id);

        characterOutService.saveCharacter(characterConverter
                                              .toSaveCharacterRequest(currentCharacter)
                                              .toBuilder()
                                              .armours(armourRequest)
                                              .build());
    }

    private int nextHarmLevel(List<HarmResponse> harms, int newHarmLevel) {
        return IntStream.range(1, 4)
            .filter(harmLevel -> harmLevel >= newHarmLevel)
            .filter(harmLevel -> hasFewerThan2OfThisLevel(harms, harmLevel))
            .min()
            .orElse(newHarmLevel);
    }

    private boolean hasFewerThan2OfThisLevel(List<HarmResponse> harms, int harmLevel) {
        return harms.stream()
                   .map(HarmResponse::level)
                   .filter(level -> level.equals(harmLevel))
                   .count() < 2;
    }

    private int howManySectionsToHeal(int result) {
        if (result <= 3) {
            return 1;
        } else if (result <= 5) {
            return 2;
        }
        return 3;
    }

    private List<SaveHarmRequest> heal(List<HarmResponse> currentHarms) {
        return currentHarms.stream()
            .filter(harm -> harm.level() > 1)
            .map(harm -> new SaveHarmRequest(harm.level() - 1, harm.detail()))
            .toList();
    }

}
