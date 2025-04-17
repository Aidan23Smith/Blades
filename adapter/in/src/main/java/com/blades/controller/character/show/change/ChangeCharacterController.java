package com.blades.controller.character.show.change;

import com.blades.converter.ErrorConverter;
import com.blades.converter.character.CharacterDisplayConverter;
import com.blades.converter.character.CharacterUpdateConverter;
import com.blades.data.character.CharacterPartDto;
import com.blades.data.character.form.CharacterChangeForm;
import com.blades.frontend.service.PageService;
import com.blades.model.requests.character.CharacterPartRequest;
import com.blades.model.requests.character.update.UpdateCharacterRequest;
import com.blades.model.requests.character.update.elements.CharacterUpdateElement;
import com.blades.model.response.character.CharacterResponse;
import com.blades.port.in.CharacterInService;
import com.blades.port.in.CrewInService;

import org.springframework.http.MediaType;
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

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ChangeCharacterController {

    private final CharacterInService characterInService;
    private final CrewInService crewInService;
    private final CharacterDisplayConverter characterDisplayConverter;
    private final CharacterUpdateConverter characterUpdateConverter;
    private final ErrorConverter errorConverter;
    private final PageService pageService;

    //todo remove userId from urls
    @GetMapping("/change/{changePart}/{userId}/{id}")
    public ModelAndView changeDetails(@PathVariable CharacterPartDto changePart,
                                      @PathVariable UUID userId,
                                      @PathVariable UUID id,
                                      CsrfToken token) {
        CharacterResponse characterResponse = characterInService.getCharacter(userId, id);
        CharacterChangeForm characterChangeForm = getPreviousAnswer(changePart, characterResponse);
        return pageService.createPage(new ChangeCharacterPage(changePart,
                                                              characterChangeForm,
                                                              characterDisplayConverter.toCrewIdDto(crewInService.getCrews()),
                                                              characterResponse.name(),
                                                              userId,
                                                              id,
                                                              Collections.emptySet(),
                                                              token.getToken()));
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
            CharacterResponse characterResponse = characterInService.getCharacter(userId, id);
            return pageService.createPage(new ChangeCharacterPage(changePart,
                                                                  characterChangeForm,
                                                                  characterDisplayConverter.toCrewIdDto(crewInService.getCrews()),
                                                                  characterResponse.name(),
                                                                  userId,
                                                                  id,
                                                                  errorConverter.toErrorDto(bindingResult),
                                                                  token.getToken()));
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
