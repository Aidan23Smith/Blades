package com.blades.controller.character.show.delete;

import com.blades.frontend.page.question.QuestionPage;

import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;

public class DeleteCharacterPage extends QuestionPage {

    DeleteCharacterPage(String characterName,
                                  UUID userId,
                                  UUID id,
                                  String token) {
        super(builder("character.delete", CHARACTERS)
                  .titleArg(characterName)
                  .backUrl("/show-characters")
                  .action("/delete/" + userId + "/" + id)
                  .csrfToken(token));
    }
}
