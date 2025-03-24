package com.blades.converter;

import com.blades.data.character.CharacterBackgroundPO;
import com.blades.data.character.CharacterHeritagePO;
import com.blades.data.character.CharacterPO;
import com.blades.data.character.CharacterTypePO;
import com.blades.data.character.CharacterVicePO;
import com.blades.model.requests.character.SaveCharacterRequest;
import com.blades.model.response.character.CharacterBackgroundResponse;
import com.blades.model.response.character.CharacterHeritageResponse;
import com.blades.model.response.character.CharacterResponse;
import com.blades.model.response.character.CharacterTypeResponse;
import com.blades.model.response.character.CharacterViceResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterConverter {

    public List<CharacterResponse> toCharacterResponses(List<CharacterPO> characters) {
        return characters.stream()
            .map(this::toCharacterResponse)
            .toList();
    }

    public CharacterResponse toCharacterResponse(CharacterPO character) {
        return new CharacterResponse(character.id(),
                                     character.owningUserId(),
                                     character.name(),
                                     character.alias().orElse(null),
                                     character.type()
                                         .map(Enum::name)
                                         .map(CharacterTypeResponse::valueOf)
                                         .orElse(null),
                                     character.crewName().orElse(null),
                                     character.look().orElse(null),
                                     character.heritage()
                                         .map(Enum::name)
                                         .map(CharacterHeritageResponse::valueOf)
                                         .orElse(null),
                                     character.background()
                                         .map(Enum::name)
                                         .map(CharacterBackgroundResponse::valueOf)
                                         .orElse(null),
                                     character.backgroundDetails().orElse(null),
                                     character.vice()
                                         .map(Enum::name)
                                         .map(CharacterViceResponse::valueOf)
                                         .orElse(null),
                                     character.viceDetails().orElse(null));
    }

    public CharacterPO toCharacterPO(SaveCharacterRequest character) {
        return new CharacterPO(character.id(),
                               character.owningUserId(),
                               character.name(),
                               character.alias().orElse(null),
                               character.type()
                                   .map(Enum::name)
                                   .map(CharacterTypePO::valueOf)
                                   .orElse(null),
                               character.crewName().orElse(null),
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
                               character.viceDetails().orElse(null));
    }

}
