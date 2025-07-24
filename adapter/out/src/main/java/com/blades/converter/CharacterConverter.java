package com.blades.converter;

import com.blades.dao.CrewDao;
import com.blades.data.character.ArmourPO;
import com.blades.data.character.CharacterBackgroundPO;
import com.blades.data.character.CharacterHeritagePO;
import com.blades.data.character.CharacterPO;
import com.blades.data.character.CharacterTypePO;
import com.blades.data.character.CharacterVicePO;
import com.blades.data.character.HarmPO;
import com.blades.data.character.TraumaPO;
import com.blades.data.crew.CrewPO;
import com.blades.model.character.Armour;
import com.blades.model.character.CharacterBackground;
import com.blades.model.character.CharacterHeritage;
import com.blades.model.character.CharacterType;
import com.blades.model.character.CharacterVice;
import com.blades.model.character.Harm;
import com.blades.model.character.Trauma;
import com.blades.model.character.Character;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CharacterConverter {

    private final CrewDao crewDao;

    public List<Character> toCharacterResponses(List<CharacterPO> characters) {
        return characters.stream()
            .map(this::toCharacterResponse)
            .toList();
    }

    public Character toCharacterResponse(CharacterPO character) {
        return new Character(character.id(),
                             character.owningUserId(),
                             character.name(),
                             character.alias().orElse(null),
                             character.type()
                                         .map(Enum::name)
                                         .map(CharacterType::valueOf)
                                         .orElse(null),
                             getCrewId(character.id()),
                             character.look().orElse(null),
                             character.heritage()
                                         .map(Enum::name)
                                         .map(CharacterHeritage::valueOf)
                                         .orElse(null),
                             character.background()
                                         .map(Enum::name)
                                         .map(CharacterBackground::valueOf)
                                         .orElse(null),
                             character.backgroundDetails().orElse(null),
                             character.vice()
                                         .map(Enum::name)
                                         .map(CharacterVice::valueOf)
                                         .orElse(null),
                             character.viceDetails().orElse(null),
                             character.stress(),
                             character.traumas().stream()
                                         .map(Enum::name)
                                         .map(Trauma::valueOf)
                                         .toList(),
                             character.harms().stream()
                                         .map(harm -> new Harm(harm.level(), harm.detail()))
                                         .toList(),
                             character.healingClock(),
                             character.armours().stream()
                                         .map(armour -> Armour.valueOf(armour.name()))
                                         .toList());
    }

    public CharacterPO toCharacterPO(Character character) {
        return new CharacterPO(character.id(),
                               character.owningUserId(),
                               character.name(),
                               character.alias().orElse(null),
                               character.type()
                                   .map(Enum::name)
                                   .map(CharacterTypePO::valueOf)
                                   .orElse(null),
                               character.look().orElse(null),
                               character.heritage()
                                   .map(Enum::name)
                                   .map(CharacterHeritagePO::valueOf)
                                   .orElse(null),
                               character.background()
                                   .map(Enum::name)
                                   .map(CharacterBackgroundPO::valueOf)
                                   .orElse(null),
                               character.backgroundDetails().orElse(null),
                               character.vice()
                                   .map(Enum::name)
                                   .map(CharacterVicePO::valueOf)
                                   .orElse(null),
                               character.viceDetails().orElse(null),
                               character.stress(),
                               character.traumas().stream()
                                   .map(Enum::name)
                                   .map(TraumaPO::valueOf)
                                   .toList(),
                               character.harms().stream()
                                   .map(harm -> new HarmPO(harm.level(), harm.detail()))
                                   .toList(),
                               character.healingClock(),
                               character.armours().stream()
                                   .map(armour -> ArmourPO.valueOf(armour.name()))
                                   .toList());
    }

    private UUID getCrewId(UUID characterId) {
        return crewDao.findAll().stream()
            .filter(crew -> crew.characterIds().contains(characterId))
            .findFirst()
            .map(CrewPO::crewId)
            .orElse(null);
    }

}
