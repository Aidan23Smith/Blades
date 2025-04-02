package com.blades.data.character;

import com.blades.frontend.page.question.OptionValueDto;

import java.util.UUID;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CrewIdDto implements OptionValueDto {

    private final String name;
    private final UUID crewId;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return crewId.toString();
    }
}
