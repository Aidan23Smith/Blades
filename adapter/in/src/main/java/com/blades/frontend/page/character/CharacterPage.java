package com.blades.frontend.page.character;

import com.blades.data.character.CharacterDto;
import com.blades.frontend.page.common.Page;

import java.util.List;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "requiredBuilder")
public class CharacterPage extends Page {

    private final List<CharacterDto> characters;

    public static CharacterPageBuilder<?, ?> builder() {
        return requiredBuilder().templateName("show-characters").groupStem("character");
    }

}
