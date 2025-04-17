package com.blades.controller.character.show.delete;

import com.blades.frontend.service.PageService;
import com.blades.model.response.character.CharacterResponse;
import com.blades.port.in.CharacterInService;
import com.blades.port.in.CrewInService;

import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DeleteCharacterController {

    private final CharacterInService characterInService;
    private final CrewInService crewInService;
    private final PageService pageService;

    @GetMapping("/delete/{userId}/{id}")
    public ModelAndView confirmDelete(@PathVariable UUID userId,
                                      @PathVariable UUID id,
                                      CsrfToken token) {
        CharacterResponse characterResponse = characterInService.getCharacter(userId, id);
        return pageService.createPage(new DeleteCharacterPage(characterResponse.name(),
                                                              userId,
                                                              id,
                                                              token.getToken()));
    }

    @PostMapping("/delete/{userId}/{id}")
    public void deleteAndRedirect(@PathVariable UUID userId,
                                  @PathVariable UUID id,
                                  HttpServletResponse response) throws IOException {
        characterInService.deleteCharacter(userId, id);
        crewInService.removeCharacter(id);
        response.sendRedirect("/show-characters");
    }

}
