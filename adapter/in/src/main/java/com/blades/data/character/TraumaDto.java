package com.blades.data.character;

import com.blades.frontend.page.question.OptionValueDto;

public enum TraumaDto implements OptionValueDto {

    COLD,
    HAUNTED,
    OBSESSED,
    PARANOID,
    RECKLESS,
    SOFT,
    UNSTABLE,
    VICIOUS,
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
