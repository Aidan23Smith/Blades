package com.blades.controller.character.play.harm;

import com.blades.data.character.ArmourDto;
import com.blades.data.error.ErrorDto;
import com.blades.frontend.page.question.Checkbox;
import com.blades.frontend.page.question.QuestionPage;

import java.util.Set;
import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;

public final class ArmourPage extends QuestionPage {

    ArmourPage(UUID characterId,
                       String token,
                       Set<ErrorDto> errors) {
        super(builder("armour", CHARACTERS)
                  .question(Checkbox.<ArmourDto>builder().values(ArmourDto.values()).questionId("selected").build().setError(errors))
                  .action("/play/" + characterId + "/armour")
                  .backUrl("/play/" + characterId)
                  .csrfToken(token));
    }

}
