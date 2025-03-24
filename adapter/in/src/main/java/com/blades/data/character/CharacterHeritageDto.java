package com.blades.data.character;

import com.blades.frontend.page.question.OptionValueDto;

public enum CharacterHeritageDto implements OptionValueDto {
    AKROS,
    THE_DAGGER_ISLES,
    IRUVIA,
    SEVEROS,
    SKOVLAN,
    TYCHEROS,
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
