package com.blades.converter.crew;

import com.blades.data.crew.dto.CharacterIdDto;
import com.blades.data.crew.dto.CrewDto;
import com.blades.model.response.character.CharacterResponse;
import com.blades.model.response.crew.CrewResponse;
import com.blades.port.in.CharacterInService;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrewDisplayConverter {

    private final CharacterInService characterInService;

    public List<CrewDto> toCrewDtos(List<CrewResponse> crews) {
        return crews.stream().map(this::toCrewDto).toList();
    }

    public CrewDto toCrewDto(CrewResponse crew) {
        return new CrewDto(crew.crewId(),
                                crew.crewName(),
                                toCharacterNames(crew.characterIds()),
                                crew.lair().orElse(null),
                                crew.lairDetails().orElse(null));
    }

    public CharacterIdDto[] toCharacterIdDto(List<CharacterResponse> characters) {
        return characters.stream().map(this::toCharacterIdDto).toArray(CharacterIdDto[]::new);
    }

    public CharacterIdDto toCharacterIdDto(CharacterResponse character) {
        return new CharacterIdDto(character.name(),
                                  character.id());
    }

    public List<String> toCharacterNames(List<UUID> characterIds) {
        return (characterIds == null) ? Collections.emptyList() : characterIds.stream().map(this::toCharacterName).collect(Collectors.toList());
    }

    public String toCharacterName(UUID characterId) {
        return characterInService.getCharacterName(characterId);
    }

}
