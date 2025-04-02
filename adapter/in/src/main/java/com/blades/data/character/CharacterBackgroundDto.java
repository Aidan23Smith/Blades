package com.blades.data.character;

import com.blades.frontend.page.question.OptionValueDto;

public enum CharacterBackgroundDto implements OptionValueDto {
    ACADEMIC,
    LABOR,
    LAW,
    TRADE,
    MILITARY,
    NOBLE,
    UNDERWORLD,
    ;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return name();
    }
}
