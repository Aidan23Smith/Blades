package com.blades.converter;

import com.blades.data.CharacterBackgroundDto;
import com.blades.data.CharacterDto;
import com.blades.data.CharacterHeritageDto;
import com.blades.data.CharacterTypeDto;
import com.blades.data.CharacterViceDto;
import com.blades.model.response.CharacterResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisplayConverter {

    public List<CharacterDto> toCharacterDtos(List<CharacterResponse> characters) {
        return characters.stream().map(this::toCharacterDto).toList();
    }

    public CharacterDto toCharacterDto(CharacterResponse character) {
        return new CharacterDto(character.owningUserId(),
                                character.id(),
                                character.name(),
                                character.alias().orElse(null),
                                character.type()
                                    .map(Enum::name)
                                    .map(CharacterTypeDto::valueOf)
                                    .orElse(null),
                                character.crewName().orElse(null),
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
                                character.viceDetails().orElse(null));
    }

}
