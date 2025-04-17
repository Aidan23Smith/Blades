package com.blades.controller.character.play.play;

import com.blades.data.character.CharacterDto;
import com.blades.data.common.Navigation;
import com.blades.frontend.page.common.Page;

import lombok.Getter;

@Getter
public class PlayPage extends Page {

    private final CharacterDto character;
    private final String csrfToken;

    protected PlayPage(CharacterDto character,
                       String csrfToken) {
        super(requiredBuilder()
                  .templateName("play")
                  .groupStem("play")
                  .currentPage(Navigation.CHARACTERS));
        this.character = character;
        this.csrfToken = csrfToken;
    }

}
