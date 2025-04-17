package com.blades.controller.character.play.harm;

import com.blades.converter.ErrorConverter;
import com.blades.converter.character.RequestCharacterConverter;
import com.blades.data.RollForm;
import com.blades.data.character.form.ArmourForm;
import com.blades.data.character.form.HarmForm;
import com.blades.frontend.service.PageService;
import com.blades.model.CustomUser;
import com.blades.port.in.HarmService;

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
public class HarmController {

    private final PageService pageService;
    private final HarmService harmService;
    private final ErrorConverter errorConverter;
    private final RequestCharacterConverter requestCharacterConverter;

    @GetMapping("/play/{characterId}/harm")
    public ModelAndView getHarmPage(@PathVariable UUID characterId,
                                    CsrfToken token) {
        return pageService.createPage(new HarmPage(characterId,
                                                   token.getToken(),
                                                   Collections.emptySet()));
    }

    @PostMapping("/play/{characterId}/harm")
    public ModelAndView addHarm(@PathVariable UUID characterId,
                                @Valid HarmForm harmForm,
                                BindingResult bindingResult,
                                Authentication authentication,
                                CsrfToken token,
                                HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            return pageService.createPage(new HarmPage(characterId,
                                                       token.getToken(),
                                                       errorConverter.toErrorDto(bindingResult)));
        }
        harmService.addHarm(((CustomUser) authentication.getPrincipal()).getUserID(),
                            characterId,
                            requestCharacterConverter.toHarmRequest(harmForm));
        response.sendRedirect("/play/" + characterId);
        return null;
    }

    @GetMapping("/play/{characterId}/heal")
    public ModelAndView getHealPage(@PathVariable UUID characterId,
                                    CsrfToken token) {
        return pageService.createPage(new HealPage(characterId,
                                                   token.getToken(),
                                                   Collections.emptySet()));
    }

    @PostMapping("/play/{characterId}/heal")
    public ModelAndView rollForHealingClock(@PathVariable UUID characterId,
                                            @Valid RollForm form,
                                            BindingResult bindingResult,
                                            Authentication authentication,
                                            CsrfToken token,
                                            HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            return pageService.createPage(new HealPage(characterId,
                                                       token.getToken(),
                                                       errorConverter.toErrorDto(bindingResult)));
        }
        harmService.rollForHealingClock(((CustomUser) authentication.getPrincipal()).getUserID(),
                                        characterId,
                                        form.roll());
        response.sendRedirect("/play/" + characterId);
        return null;
    }

    @GetMapping("/play/{characterId}/armour")
    public ModelAndView getArmourPage(@PathVariable UUID characterId,
                                      CsrfToken token) {
        return pageService.createPage(new ArmourPage(characterId,
                                                     token.getToken(),
                                                     Collections.emptySet()));
    }

    @PostMapping("/play/{characterId}/armour")
    public ModelAndView setArmour(@PathVariable UUID characterId,
                                  @Valid ArmourForm armour,
                                  BindingResult bindingResult,
                                  Authentication authentication,
                                  CsrfToken token,
                                  HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            return pageService.createPage(new ArmourPage(characterId,
                                                         token.getToken(),
                                                         errorConverter.toErrorDto(bindingResult)));
        }
        harmService.updateArmour(((CustomUser) authentication.getPrincipal()).getUserID(),
                                 characterId,
                                 requestCharacterConverter.toArmourRequest(armour.selected()));
        response.sendRedirect("/play/" + characterId);
        return null;
    }

}
