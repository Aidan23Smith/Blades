package com.blades.controller.character.play.stress;

import com.blades.data.character.TraumaDto;
import com.blades.data.character.form.TraumaForm;
import com.blades.data.error.ErrorDto;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.page.question.RadioButton;
import com.blades.model.character.Trauma;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;

public final class TraumaPage extends QuestionPage {

    TraumaPage(TraumaForm traumaForm,
               String characterName,
               List<Trauma> previousTraumas,
               UUID characterId,
               String token,
               Set<ErrorDto> errors) {
        super(builder("trauma", CHARACTERS)
                  .question(RadioButton.<TraumaDto>builder()
                                .values(Arrays.stream(TraumaDto.values())
                                            .filter(trauma -> previousTraumas.stream()
                                                .map(Enum::name)
                                                .noneMatch(t -> t.equals(trauma.getName())))
                                            .toArray(TraumaDto[]::new))
                                .previousAnswer(traumaForm.selectedName())
                                .questionId("selected")
                                .questionArg(characterName)
                                .questionArg("character.change.TRAUMA")
                                .build()
                                .setError(errors))
                  .action("/play/" + characterId + "/trauma")
                  .csrfToken(token));
    }
}
