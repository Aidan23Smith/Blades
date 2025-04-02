package com.blades.data.character;

import com.blades.frontend.page.question.OptionValueDto;

public enum CharacterViceDto implements OptionValueDto {

    FAITH,
    GAMBLING,
    LUXURY,
    OBLIGATION,
    PLEASURE,
    STUPOR,
    WEIRD,
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
