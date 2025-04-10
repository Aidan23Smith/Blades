package com.blades.data.character;

import com.blades.frontend.page.question.OptionValueDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HarmLevelDto implements OptionValueDto {

    ONE(1),
    TWO(2),
    THREE(3),
    ;

    private final int level;

    public static HarmLevelDto fromInt(int level) {
        for (HarmLevelDto value : values()) {
            if (value.level == level) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid level: " + level);
    }

    @Override
    public String getName() {
        return String.valueOf(level);
    }

    @Override
    public String getValue() {
        return name();
    }

}
