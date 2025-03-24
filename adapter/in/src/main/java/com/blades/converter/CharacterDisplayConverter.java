package com.blades.converter;

import com.blades.data.character.CharacterBackgroundDto;
import com.blades.data.character.CharacterDto;
import com.blades.data.character.CharacterHeritageDto;
import com.blades.data.character.CharacterTypeDto;
import com.blades.data.character.CharacterViceDto;
import com.blades.data.crew.CharacterIdDto;
import com.blades.model.response.character.CharacterResponse;
import com.blades.port.in.CharacterInService;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CharacterDisplayConverter {

    private final CharacterInService characterInService;

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


    public CharacterIdDto[] toCharacterIdDto(List<CharacterResponse> characters) {
        return characters.stream().map(this::toCharacterIdDto).toArray(CharacterIdDto[]::new);
    }

    public CharacterIdDto toCharacterIdDto(CharacterResponse character) {
        return new CharacterIdDto(character.name(),
                                  character.id());
    }

    public List<String> toCharacterIdStrings(List<UUID> characterIds) {
        return (characterIds == null) ? Collections.emptyList() : characterIds.stream().map(UUID::toString).collect(Collectors.toList());
    }

    public List<String> toCharacterNames(List<UUID> characterIds) {
        return (characterIds == null) ? Collections.emptyList() : characterIds.stream().map(this::toCharacterName).collect(Collectors.toList());
    }

    public String toCharacterName(UUID characterId) {
        return characterInService.getCharacterName(characterId);
    }

}
