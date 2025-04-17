package com.blades.controller.character.show.create;

import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.QuestionPage;

import java.util.List;

import static com.blades.data.common.Navigation.CHARACTERS;

public class CreateCharacterPage extends QuestionPage {

    CreateCharacterPage(String token) {
        super(builder("character.create", CHARACTERS)
                  .questions(List.of(
                      Input.builder().questionId("name").build()
                  ))
                  .action("/create-character")
                  .backUrl("/show-characters")
                  .csrfToken(token));
    }
}
