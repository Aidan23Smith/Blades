package com.blades.controller.character.play.harm;

import com.blades.data.error.ErrorDto;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.QuestionPage;

import java.util.Set;
import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;

public final class HealPage extends QuestionPage {

    HealPage(UUID characterId,
                       String token,
                       Set<ErrorDto> errors) {
        super(builder("heal", CHARACTERS)
                  .question(Input.builder().questionId("roll").build().setError(errors))
                  .action("/play/" + characterId + "/heal")
                  .backUrl("/play/" + characterId)
                  .csrfToken(token));
    }

}
