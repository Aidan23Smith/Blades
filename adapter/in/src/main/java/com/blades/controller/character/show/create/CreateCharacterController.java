package com.blades.controller.character.show.create;

import com.blades.converter.character.RequestCharacterConverter;
import com.blades.data.character.CharacterDto;
import com.blades.frontend.service.PageService;
import com.blades.model.CustomUser;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.port.in.CharacterInService;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CreateCharacterController {

    private final CharacterInService characterInService;
    private final PageService pageService;
    private final RequestCharacterConverter requestCharacterConverter;

    @GetMapping("/create-character")
    public ModelAndView getCreateCharacterPage(CsrfToken token) {
        return pageService.createPage(new CreateCharacterPage(token.getToken()));
    }

    //todo add validation
    @PostMapping("/create-character")
    public void createCharacter(CharacterDto character,
                                Authentication authentication,
                                HttpServletResponse response) throws IOException {
        CreateCharacterRequest createCharacterRequest = requestCharacterConverter
            .toCharacterRequest(character, ((CustomUser) authentication.getPrincipal()).getUserID());
        characterInService.createCharacter(createCharacterRequest);
        response.sendRedirect("/show-characters");
    }

}
