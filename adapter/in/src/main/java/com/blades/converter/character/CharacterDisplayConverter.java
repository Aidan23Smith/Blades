package com.blades.converter.character;

import com.blades.data.character.ArmourDto;
import com.blades.data.character.CharacterBackgroundDto;
import com.blades.data.character.CharacterDto;
import com.blades.data.character.CharacterHeritageDto;
import com.blades.data.character.CharacterTypeDto;
import com.blades.data.character.CharacterViceDto;
import com.blades.data.character.CrewIdDto;
import com.blades.data.character.HarmDto;
import com.blades.data.character.HarmLevelDto;
import com.blades.data.character.TraumaDto;
import com.blades.model.character.Character;
import com.blades.model.crew.Crew;
import com.blades.port.in.CrewInService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CharacterDisplayConverter {

    private final CrewInService crewInService;

    public List<CharacterDto> toCharacterDtos(List<Character> characters) {
        return characters.stream().map(this::toCharacterDto).toList();
    }

    public CharacterDto toCharacterDto(Character character) {
        return new CharacterDto(character.owningUserId(),
                                character.id(),
                                character.name(),
                                character.alias().orElse(null),
                                character.type()
                                    .map(Enum::name)
                                    .map(CharacterTypeDto::valueOf)
                                    .orElse(null),
                                toCrewName(character.crewId().orElse(null)),
                                character.look().orElse(null),
                                character.heritage()
                                    .map(Enum::name)
                                    .map(CharacterHeritageDto::valueOf)
                                    .orElse(null),
                                character.background()
                                    .map(Enum::name)
                                    .map(CharacterBackgroundDto::valueOf)
                                    .orElse(null),
                                character.backgroundDetails().orElse(null),
                                character.vice()
                                    .map(Enum::name)
                                    .map(CharacterViceDto::valueOf)
                                    .orElse(null),
                                character.viceDetails().orElse(null),
                                character.stress(),
                                character.traumas().stream()
                                    .map(Enum::name)
                                    .map(TraumaDto::valueOf)
                                    .toList(),
                                character.harms().stream()
                                    .map(harm -> new HarmDto(HarmLevelDto.fromInt(harm.level()), harm.detail()))
                                    .toList(),
                                character.healingClock(),
                                character.armours().stream()
                                    .map(armour -> ArmourDto.valueOf(armour.name()))
                                    .toList());
    }


    public CrewIdDto[] toCrewIdDto(List<Crew> crews) {
        return crews.stream().map(this::toCrewIdDto).toArray(CrewIdDto[]::new);
    }

    public CrewIdDto toCrewIdDto(Crew crew) {
        return new CrewIdDto(crew.crewName(),
                             crew.crewId());
    }

    public String toCrewName(UUID crewId) {
        return (crewId == null) ? null : crewInService.getCrewName(crewId);
    }

}
