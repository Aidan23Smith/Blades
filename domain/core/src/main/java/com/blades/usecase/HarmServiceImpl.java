package com.blades.usecase;

import com.blades.model.character.Armour;
import com.blades.model.character.Character;
import com.blades.model.character.Harm;
import com.blades.port.in.HarmService;
import com.blades.port.out.CharacterOutService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HarmServiceImpl implements HarmService {

    private final CharacterOutService characterOutService;

    @Override
    public void addHarm(UUID userId, UUID id, Harm harm) {
        Character currentCharacter = characterOutService.getCharacter(userId, id);

        Harm newHarm = new Harm(nextHarmLevel(currentCharacter.harms(), harm.level()),
                                harm.detail());

        characterOutService.saveCharacter(currentCharacter
                                              .toBuilder()
                                              .harm(newHarm)
                                              .build());
    }

    @Override
    public void rollForHealingClock(UUID userId, UUID id, int result) {
        Character currentCharacter = characterOutService.getCharacter(userId, id);

        Character.CharacterBuilder characterBuilder = currentCharacter.toBuilder();

        int healingClock = currentCharacter.healingClock() + howManySectionsToHeal(result);

        if (healingClock >= 4) {
            healingClock -= 4;
            characterBuilder.clearHarms();
            characterBuilder.harms(heal(currentCharacter.harms()));
        }

        characterOutService.saveCharacter(characterBuilder
                                              .healingClock(healingClock)
                                              .build());
    }

    @Override
    public void updateArmour(UUID userId, UUID id, List<Armour> armour) {
        Character currentCharacter = characterOutService.getCharacter(userId, id);

        characterOutService.saveCharacter(currentCharacter
                                              .toBuilder()
                                              .armours(armour)
                                              .build());
    }

    private int nextHarmLevel(List<Harm> harms, int newHarmLevel) {
        return IntStream.range(1, 4)
            .filter(harmLevel -> harmLevel >= newHarmLevel)
            .filter(harmLevel -> hasFewerThan2OfThisLevel(harms, harmLevel))
            .min()
            .orElse(newHarmLevel);
    }

    private boolean hasFewerThan2OfThisLevel(List<Harm> harms, int harmLevel) {
        return harms.stream()
                   .map(Harm::level)
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

    private List<Harm> heal(List<Harm> currentHarms) {
        return currentHarms.stream()
            .filter(harm -> harm.level() > 1)
            .map(harm -> new Harm(harm.level() - 1, harm.detail()))
            .toList();
    }

}
