package com.blades.controller.character.play.harm;

import com.blades.data.character.HarmLevelDto;
import com.blades.data.error.ErrorDto;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.page.question.RadioButton;

import java.util.Set;
import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;

public class HarmPage extends QuestionPage {

    HarmPage(UUID characterId,
                       String token,
                       Set<ErrorDto> errors) {
        super(builder("harm", CHARACTERS)
                  .question(Input.builder().questionId("harmDetail").build().setError(errors))
                  .question(RadioButton.builder().questionId("harmLevel").values(HarmLevelDto.values()).build().setError(errors))
                  .action("/play/" + characterId + "/harm")
                  .backUrl("/play/" + characterId)
                  .csrfToken(token));
    }

}
