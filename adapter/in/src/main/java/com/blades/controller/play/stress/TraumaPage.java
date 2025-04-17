package com.blades.controller.play.stress;

import com.blades.data.character.TraumaDto;
import com.blades.data.character.form.TraumaForm;
import com.blades.data.error.ErrorDto;
import com.blades.frontend.page.common.Page;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.page.question.RadioButton;
import com.blades.model.response.character.TraumaResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;

public final class TraumaPage {

    private TraumaPage() {
    }

    public static Page createPage(TraumaForm traumaForm,
                                  String characterName,
                                  List<TraumaResponse> previousTraumas,
                                  UUID characterId,
                                  String token,
                                  Set<ErrorDto> errors) {

        RadioButton<TraumaDto> traumaOptions = RadioButton.<TraumaDto>builder()
            .values(Arrays.stream(TraumaDto.values())
                        .filter(trauma -> previousTraumas.stream()
                            .map(Enum::name)
                            .noneMatch(t -> t.equals(trauma.getName())))
                        .toArray(TraumaDto[]::new))
            .previousAnswer(traumaForm.selectedName())
            .questionId("selected")
            .questionArg(characterName)
            .questionArg("character.change.TRAUMA")
            .build();

        return QuestionPage.builder("trauma", CHARACTERS)
            .question(traumaOptions.setError(errors))
            .action("/play/" + characterId + "/trauma")
            .csrfToken(token)
            .build();
    }

}
