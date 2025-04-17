package com.blades.data.character;

import com.blades.frontend.page.question.OptionValueDto;

public enum ArmourDto implements OptionValueDto {
    ARMOR,
    HEAVY,
    SPECIAL,
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
