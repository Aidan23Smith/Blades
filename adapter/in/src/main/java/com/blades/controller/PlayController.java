package com.blades.controller;

import com.blades.converter.character.CharacterDisplayConverter;
import com.blades.converter.character.RequestCharacterConverter;
import com.blades.data.RollForm;
import com.blades.data.character.HarmLevelDto;
import com.blades.data.character.TraumaDto;
import com.blades.data.character.form.CharacterChangeForm;
import com.blades.data.character.form.HarmForm;
import com.blades.frontend.page.play.PlayPage;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.page.question.RadioButton;
import com.blades.frontend.service.PageService;
import com.blades.model.CustomUser;
import com.blades.model.requests.character.TraumaRequest;
import com.blades.model.response.character.CharacterResponse;
import com.blades.port.in.CharacterInService;
import com.blades.port.in.HarmService;
import com.blades.port.in.StressService;

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
import java.util.Arrays;
import java.util.UUID;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import static com.blades.data.common.Navigation.CHARACTERS;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PlayController {

    private final CharacterInService characterInService;
    private final StressService stressService;
    private final HarmService harmService;
    private final PageService pageService;
    private final CharacterDisplayConverter characterDisplayConverter;
    private final RequestCharacterConverter requestCharacterConverter;

    @GetMapping("/play/{characterId}")
    public ModelAndView getPlayPage(@PathVariable UUID characterId,
                                    Authentication authentication,
                                    CsrfToken token) {
        return pageService.createPage(
            PlayPage.builder()
                .character(characterDisplayConverter.toCharacterDto(
                    characterInService.getCharacter(((CustomUser) authentication.getPrincipal()).getUserID(),
                                                    characterId)))
                .csrfToken(token.getToken())
                .build());
    }

    @PostMapping("/play/{characterId}/stress/increase")
    public void increaseStress(@PathVariable UUID characterId,
                               Authentication authentication,
                               HttpServletResponse response) throws IOException {
        stressService.increaseStress(((CustomUser) authentication.getPrincipal()).getUserID(),
                                     characterId);
        if (stressService.isTooStressed(((CustomUser) authentication.getPrincipal()).getUserID(),
                                        characterId)) {
            response.sendRedirect("/play/" + characterId + "/trauma");
            return;
        }
        response.sendRedirect("/play/" + characterId);
    }

    @PostMapping("/play/{characterId}/stress/decrease")
    public void decreaseStress(@PathVariable UUID characterId,
                               Authentication authentication,
                               HttpServletResponse response) throws IOException {
        stressService.decreaseStress(((CustomUser) authentication.getPrincipal()).getUserID(),
                                     characterId);
        response.sendRedirect("/play/" + characterId);
    }

    @GetMapping("/play/{characterId}/trauma")
    public ModelAndView getTraumaPage(@PathVariable UUID characterId,
                                      Authentication authentication,
                                      CharacterChangeForm characterChangeForm,
                                      String errorProperty,
                                      CsrfToken token) {
        CharacterResponse characterResponse = characterInService.getCharacter(((CustomUser) authentication.getPrincipal()).getUserID(), characterId);

        RadioButton traumaOptions = RadioButton.<TraumaDto>builder()
            .values(Arrays.stream(TraumaDto.values())
                        .filter(trauma -> characterResponse.traumas().stream()
                            .map(Enum::name)
                            .noneMatch(t -> t.equals(trauma.getName())))
                        .toArray(TraumaDto[]::new))
            .previousAnswer(characterChangeForm.getSingleElement())
            .questionId("changeElement")
            .questionArg(characterResponse.name())
            .questionArg("character.change.TRAUMA")
            .errorProperty(errorProperty)
            .build();

        return pageService.createPage(QuestionPage.builder("trauma", CHARACTERS)
                                          .question(traumaOptions)
                                          .action("/play/" + characterId + "/trauma")
                                          .csrfToken(token.getToken()) //todo add back
                                          .build());
    }

    @PostMapping("/play/{characterId}/trauma")
    public ModelAndView setTrauma(@PathVariable UUID characterId,
                                  Authentication authentication,
                                  @Valid CharacterChangeForm characterChangeForm,
                                  BindingResult bindingResult,
                                  HttpServletResponse response,
                                  CsrfToken token) throws IOException {
        if (bindingResult.hasErrors()) {
            return getTraumaPage(characterId,
                                 authentication,
                                 (characterChangeForm.changeElement() == null) ? new CharacterChangeForm() : characterChangeForm,
                                 bindingResult.getAllErrors().stream()
                                     .map(error -> error.getDefaultMessage() + "TRAUMA")
                                     .toList()
                                     .getFirst(), //todo generalise errors by including questionId
                                 token);
        }
        stressService.setNewTrauma(((CustomUser) authentication.getPrincipal()).getUserID(),
                                   characterId,
                                   TraumaRequest.valueOf(characterChangeForm.getSingleElement()));

        response.sendRedirect("/play/" + characterId);
        return null;
    }

    @GetMapping("/play/{characterId}/harm")
    public ModelAndView getHarmPage(@PathVariable UUID characterId,
                                    CsrfToken token,
                                    String errorProperty) {
        return pageService.createPage(
            QuestionPage.builder("harm", CHARACTERS)
                .question(Input.builder().questionId("harmDetail").errorProperty(errorProperty).build())
                .question(RadioButton.builder().questionId("harmLevel").errorProperty(errorProperty).values(HarmLevelDto.values()).build())
                .action("/play/" + characterId + "/harm")
                .backUrl("/play/" + characterId)
                .csrfToken(token.getToken())
                .build());
    }

    @PostMapping("/play/{characterId}/harm")
    public ModelAndView addHarm(@PathVariable UUID characterId,
                                @Valid HarmForm harmForm,
                                BindingResult bindingResult,
                                Authentication authentication,
                                CsrfToken token,
                                HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            return getHarmPage(characterId, token, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        harmService.addHarm(((CustomUser) authentication.getPrincipal()).getUserID(),
                            characterId,
                            requestCharacterConverter.toHarmRequest(harmForm));
        response.sendRedirect("/play/" + characterId);
        return null;
    }

    @GetMapping("/play/{characterId}/heal")
    public ModelAndView getHealPage(@PathVariable UUID characterId,
                                    CsrfToken token,
                                    String errorProperty) {
        return pageService.createPage(
            QuestionPage.builder("heal", CHARACTERS)
                .question(Input.builder().questionId("roll").errorProperty(errorProperty).build())
                .action("/play/" + characterId + "/heal")
                .backUrl("/play/" + characterId)
                .csrfToken(token.getToken())
                .build());
    }

    @PostMapping("/play/{characterId}/heal")
    public ModelAndView rollForHealingClock(@PathVariable UUID characterId,
                                            @Valid RollForm form,
                                            BindingResult bindingResult,
                                            Authentication authentication,
                                            CsrfToken token,
                                            HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            return getHealPage(characterId, token, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        harmService.rollForHealingClock(((CustomUser) authentication.getPrincipal()).getUserID(),
                                        characterId,
                                        form.roll());
        response.sendRedirect("/play/" + characterId);
        return null;
    }

}
