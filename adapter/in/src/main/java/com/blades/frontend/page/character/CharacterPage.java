package com.blades.frontend.page.character;

import com.blades.data.CharacterDto;
import com.blades.frontend.page.common.Page;

import java.util.List;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "requiredBuilder")
public class CharacterPage extends Page {

    private final List<CharacterDto> characters;

    @Override
    public String getTemplateName() {
        return "show";
    }

    public static CharacterPageBuilder<?, ?> builder() {
        return requiredBuilder().templateName("show").groupStem("character");
    }

}
