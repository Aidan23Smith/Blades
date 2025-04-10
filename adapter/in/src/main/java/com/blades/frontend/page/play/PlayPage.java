package com.blades.frontend.page.play;

import com.blades.data.character.CharacterDto;
import com.blades.data.common.Navigation;
import com.blades.frontend.page.common.Page;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "requiredBuilder")
public class PlayPage extends Page {

    private final CharacterDto character;
    private final String csrfToken;

    public static PlayPageBuilder<?, ?> builder() {
        return requiredBuilder()
            .templateName("play")
            .groupStem("play")
            .currentPage(Navigation.CHARACTERS);
    }

}
