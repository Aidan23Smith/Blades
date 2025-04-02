package com.blades.controller;

import com.blades.converter.character.CharacterDisplayConverter;
import com.blades.converter.character.CharacterUpdateConverter;
import com.blades.converter.character.RequestCharacterConverter;
import com.blades.data.character.CharacterBackgroundDto;
import com.blades.data.character.CharacterDto;
import com.blades.data.character.CharacterHeritageDto;
import com.blades.data.character.CharacterPartDto;
import com.blades.data.character.CharacterTypeDto;
import com.blades.data.character.CharacterViceDto;
import com.blades.data.character.CrewIdDto;
import com.blades.data.character.form.CharacterChangeForm;
import com.blades.frontend.page.character.CharacterPage;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.Question;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.page.question.RadioButton;
import com.blades.frontend.service.PageService;
import com.blades.model.CustomUser;
import com.blades.model.requests.character.CharacterPartRequest;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.update.UpdateCharacterRequest;
import com.blades.model.requests.character.update.elements.CharacterUpdateElement;
import com.blades.model.response.character.CharacterResponse;
import com.blades.port.in.CharacterInService;
import com.blades.port.in.CrewInService;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import static com.blades.data.common.Navigation.CHARACTERS;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CharacterController {

    private final CharacterInService characterInService;
    private final CrewInService crewInService;
    private final PageService pageService;
    private final RequestCharacterConverter requestCharacterConverter;
    private final CharacterDisplayConverter characterDisplayConverter;
    private final CharacterUpdateConverter characterUpdateConverter;

    @GetMapping("/create-character")
    public ModelAndView getCreateCharacterPage(CsrfToken token) {
        return pageService.createPage(QuestionPage.builder("character.create", CHARACTERS)
                                          .questions(List.of(
                                              Input.builder().questionId("name").build()
                                          ))
                                          .action("/create-character")
                                          .backUrl("/show-characters")
                                          .csrfToken(token.getToken()).build());
    }

    @PostMapping("/create-character")
    public void createCharacter(CharacterDto character,
                                Authentication authentication,
                                HttpServletResponse response) throws IOException {
        CreateCharacterRequest createCharacterRequest = requestCharacterConverter
            .toCharacterRequest(character, ((CustomUser) authentication.getPrincipal()).getUserID());
        characterInService.createCharacter(createCharacterRequest);
        response.sendRedirect("/show-characters");
    }

    @GetMapping("/show-characters")
    public ModelAndView showCharacter(Authentication authentication) {
        List<CharacterResponse> characterResponses = characterInService.getCharacters(((CustomUser) authentication.getPrincipal()).getUserID());
        return pageService.createPage(CharacterPage.builder()
                                          .characters(characterDisplayConverter.toCharacterDtos(characterResponses))
                                          .build());
    }

    @GetMapping("/delete/{userId}/{id}")
    public ModelAndView confirmDelete(@PathVariable UUID userId,
                                      @PathVariable UUID id,
                                      CsrfToken token) {
        CharacterResponse characterResponse = characterInService.getCharacter(userId, id);
        return pageService.createPage(QuestionPage.builder("character.delete", CHARACTERS)
                                          .titleArgs(List.of(characterResponse.name()))
                                          .backUrl("/show-characters")
                                          .action("/delete/" + userId + "/" + id)
                                          .csrfToken(token.getToken())
                                          .build());
    }

    @PostMapping("/delete/{userId}/{id}")
    public void deleteAndRedirect(@PathVariable UUID userId,
                                  @PathVariable UUID id,
                                  HttpServletResponse response) throws IOException {
        characterInService.deleteCharacter(userId, id);
        crewInService.removeCharacter(id);
        response.sendRedirect("/show-characters");
    }

    @GetMapping("/change/{changePart}/{userId}/{id}")
    public ModelAndView changeDetails(@PathVariable CharacterPartDto changePart,
                                      @PathVariable UUID userId,
                                      @PathVariable UUID id,
                                      CharacterChangeForm characterChangeForm,
                                      String errorProperty,
                                      CsrfToken token) {
        CharacterResponse characterResponse = characterInService.getCharacter(userId, id);
        characterChangeForm = (characterChangeForm.changeElement() == null) ? getPreviousAnswer(changePart, characterResponse) : characterChangeForm;

        Question.QuestionBuilder builder = switch (changePart) {
            case NAME, ALIAS, LOOK, BACKGROUND_DETAILS, VICE_DETAILS -> Input.builder().previousAnswer(characterChangeForm.getSingleElement());
            case TYPE -> RadioButton.<CharacterTypeDto>builder().values(CharacterTypeDto.values()).previousAnswer(characterChangeForm.getSingleElement());
            case HERITAGE -> RadioButton.<CharacterHeritageDto>builder().values(CharacterHeritageDto.values()).previousAnswer(characterChangeForm.getSingleElement());
            case BACKGROUND -> RadioButton.<CharacterBackgroundDto>builder().values(CharacterBackgroundDto.values()).previousAnswer(characterChangeForm.getSingleElement());
            case VICE -> RadioButton.<CharacterViceDto>builder().values(CharacterViceDto.values()).previousAnswer(characterChangeForm.getSingleElement());
            case CREW_NAME -> RadioButton.<CrewIdDto>builder()
                .values(characterDisplayConverter.toCrewIdDto(crewInService.getCrews()))
                .previousAnswers(characterChangeForm.changeElement());
        };

        builder.questionId("changeElement")
            .questionArg(characterResponse.name())
            .questionArg("character.change." + changePart)
            .errorProperty(errorProperty);
        return pageService.createPage(QuestionPage.builder("character.change", CHARACTERS)
                                          .question(builder.build())
                                          .action("/change/" + changePart + "/" + userId + "/" + id)
                                          .backUrl("/show-characters")
                                          .csrfToken(token.getToken())
                                          .build());
    }

    @PostMapping("/change/{changePart}/{userId}/{id}")
    public ModelAndView editAndRedirect(@PathVariable CharacterPartDto changePart,
                                        @PathVariable UUID userId,
                                        @PathVariable UUID id,
                                        @Valid CharacterChangeForm characterChangeForm,
                                        BindingResult bindingResult,
                                        HttpServletResponse response,
                                        CsrfToken token) throws IOException {
        if (bindingResult.hasErrors()) {
            return changeDetails(changePart,
                                 userId,
                                 id,
                                 (characterChangeForm.changeElement() == null) ? new CharacterChangeForm() : characterChangeForm,
                                 bindingResult.getAllErrors().stream()
                                     .map(error -> error.getDefaultMessage() + changePart)
                                     .toList()
                                     .getFirst(), //todo generalise errors by including questionId
                                 token);
        }

        CharacterUpdateElement changeElement = switch (changePart) {
            case CREW_NAME -> characterUpdateConverter.toCharacterUpdateUUID(characterChangeForm);
            default -> characterUpdateConverter.toCharacterUpdateString(characterChangeForm);
        };
        characterInService.updateCharacter(new UpdateCharacterRequest(userId,
                                                                      id,
                                                                      CharacterPartRequest.valueOf(changePart.name()),
                                                                      changeElement));
        response.sendRedirect("/show-characters");
        return null;
    }

    private CharacterChangeForm getPreviousAnswer(CharacterPartDto changePart, CharacterResponse characterResponse) {
        return new CharacterChangeForm(
            switch (changePart) {
                case NAME -> List.of(characterResponse.name());
                case ALIAS -> characterResponse.alias().map(List::of).orElse(Collections.emptyList());
                case TYPE -> characterResponse.type().map(Enum::name).map(List::of).orElse(Collections.emptyList());
                case CREW_NAME -> characterResponse.crewId().isEmpty() ? Collections.emptyList() : characterResponse.crewId().stream().map(UUID::toString).toList();
                case LOOK -> characterResponse.look().map(List::of).orElse(Collections.emptyList());
                case HERITAGE -> characterResponse.heritage().map(Enum::name).map(List::of).orElse(Collections.emptyList());
                case BACKGROUND -> characterResponse.background().map(Enum::name).map(List::of).orElse(Collections.emptyList());
                case BACKGROUND_DETAILS -> characterResponse.backgroundDetails().map(List::of).orElse(Collections.emptyList());
                case VICE -> characterResponse.vice().map(Enum::name).map(List::of).orElse(Collections.emptyList());
                case VICE_DETAILS -> characterResponse.viceDetails().map(List::of).orElse(Collections.emptyList());
            });
    }

}
