package com.blades.controller.character.show.change;

import com.blades.data.character.CharacterBackgroundDto;
import com.blades.data.character.CharacterHeritageDto;
import com.blades.data.character.CharacterPartDto;
import com.blades.data.character.CharacterTypeDto;
import com.blades.data.character.CharacterViceDto;
import com.blades.data.character.CrewIdDto;
import com.blades.data.character.form.CharacterChangeForm;
import com.blades.data.error.ErrorDto;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.page.question.RadioButton;

import java.util.Set;
import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;

public class ChangeCharacterPage extends QuestionPage {

    ChangeCharacterPage(CharacterPartDto changePart,
                                  CharacterChangeForm characterChangeForm,
                                  CrewIdDto[] crewsIds,
                                  String characterName,
                                  UUID userId,
                                  UUID id,
                                  Set<ErrorDto> errors,
                                  String token) {
        super(builder("character.change", CHARACTERS)
                  .question((switch (changePart) {
                      case NAME, ALIAS, LOOK, BACKGROUND_DETAILS, VICE_DETAILS -> Input.builder().previousAnswer(characterChangeForm.getSingleElement());
                      case TYPE -> RadioButton.<CharacterTypeDto>builder().values(CharacterTypeDto.values()).previousAnswer(characterChangeForm.getSingleElement());
                      case HERITAGE -> RadioButton.<CharacterHeritageDto>builder().values(CharacterHeritageDto.values()).previousAnswer(characterChangeForm.getSingleElement());
                      case BACKGROUND -> RadioButton.<CharacterBackgroundDto>builder().values(CharacterBackgroundDto.values()).previousAnswer(characterChangeForm.getSingleElement());
                      case VICE -> RadioButton.<CharacterViceDto>builder().values(CharacterViceDto.values()).previousAnswer(characterChangeForm.getSingleElement());
                      case CREW_NAME -> RadioButton.<CrewIdDto>builder()
                          .values(crewsIds)
                          .previousAnswer(characterChangeForm.getSingleElement());
                  }).questionId("changeElement")
                                .questionArg(characterName)
                                .questionArg("character.change." + changePart).build().setError(errors))
                  .action("/change/" + changePart + "/" + userId + "/" + id)
                  .backUrl("/show-characters")
                  .csrfToken(token));
    }
}
