package com.blades.controller;

import com.blades.converter.DisplayConverter;
import com.blades.converter.RequestCharacterConverter;
import com.blades.data.character.CharacterBackgroundDto;
import com.blades.data.character.CharacterDto;
import com.blades.data.character.CharacterHeritageDto;
import com.blades.data.character.CharacterPartDto;
import com.blades.data.character.CharacterTypeDto;
import com.blades.data.character.CharacterViceDto;
import com.blades.frontend.page.character.CharacterPage;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.Question;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.page.question.RadioButton;
import com.blades.frontend.service.PageService;
import com.blades.model.CustomUser;
import com.blades.model.requests.CharacterPartRequest;
import com.blades.model.requests.CreateCharacterRequest;
import com.blades.model.requests.UpdateCharacterRequest;
import com.blades.model.response.CharacterResponse;
import com.blades.port.in.CharacterInService;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CharacterController {

    private final CharacterInService characterInService;
    private final PageService pageService;
    private final RequestCharacterConverter requestCharacterConverter;
    private final DisplayConverter displayConverter;

    @GetMapping("/create-character")
    public ModelAndView getCreateCharacterPage(CsrfToken token) {
        return pageService.createPage(QuestionPage.builder("character.create")
                                          .questions(List.of(
                                              Input.builder().questionId("name").build()
                                          ))
                                          .action("/create-character")
                                          .backUrl("/show-character")
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
                                          .characters(displayConverter.toCharacterDtos(characterResponses))
                                          .build());
    }

    @GetMapping("/delete/{userId}/{id}")
    public ModelAndView confirmDelete(@PathVariable UUID userId,
                                      @PathVariable UUID id,
                                      CsrfToken token) {
        CharacterResponse characterResponse = characterInService.getCharacter(userId, id);
        return pageService.createPage(QuestionPage.builder("character.delete")
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
        response.sendRedirect("/show-characters");
    }

    @GetMapping("/change/{changePart}/{userId}/{id}")
    public ModelAndView changeDetails(@PathVariable CharacterPartDto changePart,
                                      @PathVariable UUID userId,
                                      @PathVariable UUID id,
                                      String previousAnswer,
                                      String errorProperty,
                                      CsrfToken token) {
        CharacterResponse characterResponse = characterInService.getCharacter(userId, id);
        previousAnswer = (previousAnswer == null) ? getPreviousAnswer(changePart, characterResponse) : previousAnswer;
        Question question = switch (changePart) {
            case NAME, ALIAS, CREW_NAME, LOOK, BACKGROUND_DETAILS, VICE_DETAILS -> Input.builder()
                .questionId("changeElement")
                .questionArg(characterResponse.name())
                .questionArg("character.change." + changePart)
                .previousAnswer(previousAnswer)
                .errorProperty(errorProperty)
                .build();
            case TYPE -> toChangeRadioButton(changePart, characterResponse, previousAnswer, CharacterTypeDto.values(), errorProperty);
            case HERITAGE -> toChangeRadioButton(changePart, characterResponse, previousAnswer, CharacterHeritageDto.values(), errorProperty);
            case BACKGROUND -> toChangeRadioButton(changePart, characterResponse, previousAnswer, CharacterBackgroundDto.values(), errorProperty);
            case VICE -> toChangeRadioButton(changePart, characterResponse, previousAnswer, CharacterViceDto.values(), errorProperty);
        };
        return pageService.createPage(QuestionPage.builder("character.change")
                                          .question(question)
                                          .action("/change/" + changePart + "/" + userId + "/" + id)
                                          .backUrl("/show-characters")
                                          .csrfToken(token.getToken())
                                          .build());
    }

    @PostMapping("/change/{changePart}/{userId}/{id}")
    public ModelAndView editAndRedirect(@PathVariable CharacterPartDto changePart,
                                        @PathVariable UUID userId,
                                        @PathVariable UUID id,
                                        @RequestParam String changeElement, //todo make accept no changeElement
                                        HttpServletResponse response,
                                        CsrfToken token) throws IOException {
        if ((changeElement == null) || changeElement.isEmpty()) {
            return changeDetails(changePart,
                                 userId,
                                 id,
                                 "",
                                 "no.value." + changePart,
                                 token);
        }

        characterInService.updateCharacter(new UpdateCharacterRequest(userId,
                                                                      id,
                                                                      CharacterPartRequest.valueOf(changePart.name()),
                                                                      changeElement));
        response.sendRedirect("/show-characters");
        return null;
    }

    private String getPreviousAnswer(CharacterPartDto changePart, CharacterResponse characterResponse) {
        return switch (changePart) {
            case NAME -> characterResponse.name();
            case ALIAS -> characterResponse.alias().orElse(null);
            case TYPE -> characterResponse.type().map(Enum::name).orElse(null);
            case CREW_NAME -> characterResponse.crewName().orElse(null);
            case LOOK -> characterResponse.look().orElse(null);
            case HERITAGE -> characterResponse.heritage().map(Enum::name).orElse(null);
            case BACKGROUND -> characterResponse.background().map(Enum::name).orElse(null);
            case BACKGROUND_DETAILS -> characterResponse.backgroundDetails().orElse(null);
            case VICE -> characterResponse.vice().map(Enum::name).orElse(null);
            case VICE_DETAILS -> characterResponse.viceDetails().orElse(null);
        };
    }

    private <T extends Enum<T>> RadioButton<T> toChangeRadioButton(CharacterPartDto changePart,
                                                                   CharacterResponse characterResponse,
                                                                   String previousAnswer,
                                                                   T[] values,
                                                                   String errorProperty) {
        return RadioButton.<T>builder()
            .questionId("changeElement")
            .values(values)
            .questionArg(characterResponse.name())
            .questionArg("character.change." + changePart)
            .previousAnswer(previousAnswer)
            .errorProperty(errorProperty)
            .build();
    }

}
