package com.blades.controller.character.show.show;

import com.blades.data.character.CharacterDto;
import com.blades.frontend.page.common.Page;

import java.util.List;

import lombok.Getter;

import static com.blades.data.common.Navigation.CHARACTERS;

@Getter
public class ShowCharactersPage extends Page {

    private final List<CharacterDto> characters;

    ShowCharactersPage(List<CharacterDto> characters) {
        super(builder("show-characters",
                      "character",
                      CHARACTERS));

        this.characters = characters;
    }
}
