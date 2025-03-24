package com.blades.data.character;

import com.blades.frontend.page.question.OptionValueDto;

public enum CharacterTypeDto implements OptionValueDto {

    CUTTER,
    HOUND,
    LEECH,
    LURK,
    SLIDE,
    SPIDER,
    WHISPER,
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
