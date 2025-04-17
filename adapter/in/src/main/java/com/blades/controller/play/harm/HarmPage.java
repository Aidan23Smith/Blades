package com.blades.controller.play.harm;

import com.blades.data.character.HarmLevelDto;
import com.blades.data.error.ErrorDto;
import com.blades.frontend.page.common.Page;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.page.question.RadioButton;

import java.util.Set;
import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;

public final class HarmPage {

    private HarmPage() {
    }

    public static Page createPage(UUID characterId,
                                  String token,
                                  Set<ErrorDto> errors) {

        return QuestionPage.builder("harm", CHARACTERS)
            .question(Input.builder().questionId("harmDetail").build().setError(errors))
            .question(RadioButton.builder().questionId("harmLevel").values(HarmLevelDto.values()).build().setError(errors))
            .action("/play/" + characterId + "/harm")
            .backUrl("/play/" + characterId)
            .csrfToken(token)
            .build();
    }

}
