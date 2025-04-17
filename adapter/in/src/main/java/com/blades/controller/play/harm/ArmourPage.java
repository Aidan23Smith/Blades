package com.blades.controller.play.harm;

import com.blades.data.character.ArmourDto;
import com.blades.data.error.ErrorDto;
import com.blades.frontend.page.common.Page;
import com.blades.frontend.page.question.Checkbox;
import com.blades.frontend.page.question.QuestionPage;

import java.util.Set;
import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;

public final class ArmourPage {

    private ArmourPage() {
    }

    public static Page createPage(UUID characterId,
                                  String token,
                                  Set<ErrorDto> errors) {

        return QuestionPage.builder("armour", CHARACTERS)
            .question(Checkbox.<ArmourDto>builder().values(ArmourDto.values()).questionId("selected").build().setError(errors))
            .action("/play/" + characterId + "/armour")
            .backUrl("/play/" + characterId)
            .csrfToken(token)
            .build();
    }

}
