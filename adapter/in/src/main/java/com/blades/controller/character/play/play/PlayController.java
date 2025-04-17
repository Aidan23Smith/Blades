package com.blades.controller.character.play.play;

import com.blades.converter.character.CharacterDisplayConverter;
import com.blades.frontend.service.PageService;
import com.blades.model.CustomUser;
import com.blades.port.in.CharacterInService;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class PlayController {

    private final CharacterInService characterInService;
    private final PageService pageService;
    private final CharacterDisplayConverter characterDisplayConverter;

    @GetMapping("/play/{characterId}")
    public ModelAndView getPlayPage(@PathVariable UUID characterId,
                                    Authentication authentication,
                                    CsrfToken token) {
        return pageService.createPage(
            new PlayPage(characterDisplayConverter.toCharacterDto(characterInService.getCharacter(((CustomUser) authentication.getPrincipal()).getUserID(), characterId)),
                         token.getToken()));
    }

}
