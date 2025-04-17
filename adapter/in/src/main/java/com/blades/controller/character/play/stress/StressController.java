package com.blades.controller.character.play.stress;

import com.blades.converter.ErrorConverter;
import com.blades.data.character.form.TraumaForm;
import com.blades.frontend.service.PageService;
import com.blades.model.CustomUser;
import com.blades.model.requests.character.TraumaRequest;
import com.blades.model.response.character.CharacterResponse;
import com.blades.port.in.CharacterInService;
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
import java.util.Collections;
import java.util.UUID;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class StressController {

    private final PageService pageService;
    private final CharacterInService characterInService;
    private final StressService stressService;
    private final ErrorConverter errorConverter;

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
                                      TraumaForm traumaForm,
                                      CsrfToken token) {
        CharacterResponse characterResponse = characterInService.getCharacter(((CustomUser) authentication.getPrincipal()).getUserID(), characterId);

        return pageService.createPage(new TraumaPage(traumaForm,
                                                     characterResponse.name(),
                                                     characterResponse.traumas(),
                                                     characterId,
                                                     token.getToken(),
                                                     Collections.emptySet()));
    }

    @PostMapping("/play/{characterId}/trauma")
    public ModelAndView setTrauma(@PathVariable UUID characterId,
                                  Authentication authentication,
                                  @Valid TraumaForm traumaForm,
                                  BindingResult bindingResult,
                                  HttpServletResponse response,
                                  CsrfToken token) throws IOException {
        if (bindingResult.hasErrors()) {
            CharacterResponse characterResponse = characterInService.getCharacter(((CustomUser) authentication.getPrincipal()).getUserID(), characterId);
            return pageService.createPage(new TraumaPage(traumaForm,
                                                         characterResponse.name(),
                                                         characterResponse.traumas(),
                                                         characterId,
                                                         token.getToken(),
                                                         errorConverter.toErrorDto(bindingResult)));
        }
        stressService.setNewTrauma(((CustomUser) authentication.getPrincipal()).getUserID(),
                                   characterId,
                                   TraumaRequest.valueOf(traumaForm.selectedName()));

        response.sendRedirect("/play/" + characterId);
        return null;
    }

}
