package com.blades.data.crew;

import com.blades.frontend.page.question.OptionValueDto;

import java.util.UUID;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CharacterIdDto implements OptionValueDto {

    private final String name;
    private final UUID characterId;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return characterId.toString();
    }
}
