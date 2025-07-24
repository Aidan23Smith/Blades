package com.blades.controller;

import com.blades.converter.ErrorConverter;
import com.blades.converter.crew.CrewDisplayConverter;
import com.blades.converter.crew.CrewRequestConverter;
import com.blades.converter.crew.CrewUpdateConverter;
import com.blades.data.crew.dto.CharacterIdDto;
import com.blades.data.crew.dto.CrewDto;
import com.blades.data.crew.dto.CrewPartDto;
import com.blades.data.crew.form.CrewChangeForm;
import com.blades.data.error.ErrorDto;
import com.blades.frontend.page.crew.CrewPage;
import com.blades.frontend.page.question.Checkbox;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.Question;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.service.PageService;
import com.blades.model.requests.crew.CreateCrewRequest;
import com.blades.model.requests.crew.CrewPartRequest;
import com.blades.model.requests.crew.update.UpdateCrewRequest;
import com.blades.model.requests.crew.update.elements.CrewUpdateElement;
import com.blades.model.crew.Crew;
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
import java.util.Set;
import java.util.UUID;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import static com.blades.data.common.Navigation.CREWS;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CrewController {

    private final CrewInService crewInService;
    private final PageService pageService;
    private final CharacterInService characterInService;
    private final CrewRequestConverter crewRequestConverter;
    private final CrewDisplayConverter crewDisplayConverter;
    private final CrewUpdateConverter crewUpdateConverter;
    private final ErrorConverter errorConverter;

    @GetMapping("/crew/create-crew")
    public ModelAndView getCreateCrewPage(CsrfToken token) {
        return pageService.createPage(QuestionPage.builder("crew.create", CREWS)
                                          .questions(List.of(
                                              Input.builder().questionId("crewName").build()
                                          ))
                                          .action("/crew/create-crew")
                                          .backUrl("/crew/show-crews")
                                          .csrfToken(token.getToken()).build());
    }

    @PostMapping("/crew/create-crew")
    public void createCrew(CrewDto crew,
                           HttpServletResponse response) throws IOException {
        CreateCrewRequest createCrewRequest = crewRequestConverter
            .toCrewRequest(crew);
        crewInService.createCrew(createCrewRequest);
        response.sendRedirect("/crew/show-crews");
    }

    @GetMapping("/crew/show-crews")
    public ModelAndView showCrew() {
        List<Crew> crewRespons = crewInService.getCrews();
        return pageService.createPage(CrewPage.builder()
                                          .crews(crewDisplayConverter.toCrewDtos(crewRespons))
                                          .build());
    }

    @GetMapping("/crew/delete/{crewId}")
    public ModelAndView confirmDelete(@PathVariable UUID crewId,
                                      CsrfToken token) {
        Crew crew = crewInService.getCrew(crewId);
        return pageService.createPage(QuestionPage.builder("crew.delete", CREWS)
                                          .titleArgs(List.of(crew.crewName()))
                                          .backUrl("/crew/show-crews")
                                          .action("/crew/delete/" + crewId)
                                          .csrfToken(token.getToken())
                                          .build());
    }

    @PostMapping(path = "/crew/delete/{crewId}", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public void deleteAndRedirect(@PathVariable UUID crewId,
                                  HttpServletResponse response) throws IOException {
        crewInService.deleteCrew(crewId);
        characterInService.removeCrew(crewId);
        response.sendRedirect("/crew/show-crews");
    }

    @GetMapping("/crew/change/{changePart}/{crewId}")
    public ModelAndView changeDetails(@PathVariable CrewPartDto changePart,
                                      @PathVariable UUID crewId,
                                      CrewChangeForm crewChangeForm,
                                      CsrfToken token) {
        return getChangeDetailsPage(changePart,
                                    crewId,
                                    crewChangeForm,
                                    Collections.emptySet(),
                                    token);
    }

    @PostMapping(value = "/crew/change/{changePart}/{crewId}", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView editAndRedirect(@PathVariable CrewPartDto changePart,
                                        @PathVariable UUID crewId,
                                        @Valid CrewChangeForm crewChangeForm,
                                        BindingResult bindingResult,
                                        HttpServletResponse response,
                                        CsrfToken token) throws IOException {

        if (bindingResult.hasErrors()) {
            return getChangeDetailsPage(changePart,
                                        crewId,
                                        (crewChangeForm.changeElement() == null) ? new CrewChangeForm() : crewChangeForm,
                                        errorConverter.toErrorDto(bindingResult),
                                        token);
        }

        CrewUpdateElement updateElement = switch (changePart) {
            case CREW_NAME, LAIR, LAIR_DETAILS -> crewUpdateConverter.toCrewUpdateString(crewChangeForm);
            case CHARACTER_IDS -> crewUpdateConverter.toCrewUpdateUUIDList(crewChangeForm);
        };
        crewInService.updateCrew(new UpdateCrewRequest(crewId,
                                                       CrewPartRequest.valueOf(changePart.name()),
                                                       updateElement));

        response.sendRedirect("/crew/show-crews");
        return null;
    }

    private ModelAndView getChangeDetailsPage(CrewPartDto changePart,
                                              UUID crewId,
                                              CrewChangeForm crewChangeForm,
                                              Set<ErrorDto> errors,
                                              CsrfToken token) {
        Crew crew = crewInService.getCrew(crewId);
        crewChangeForm = (crewChangeForm.changeElement() == null) ? getPreviousAnswer(changePart, crew) : crewChangeForm;

        Question.QuestionBuilder builder = switch (changePart) {
            case CREW_NAME, LAIR, LAIR_DETAILS -> Input.builder().previousAnswer(crewChangeForm.changeElement().getFirst());
            case CHARACTER_IDS -> Checkbox.<CharacterIdDto>builder()
                .values(crewDisplayConverter.toCharacterIdDto(characterInService.getAllCharacters()))
                .previousAnswers(crewChangeForm.changeElement());

        };

        builder.questionId("changeElement")
            .questionArg(crew.crewName())
            .questionArg("crew.change." + changePart)
            .build()
            .setError(errors);
        return pageService.createPage(QuestionPage.builder("crew.change", CREWS)
                                          .question(builder.build())
                                          .action("/crew/change/" + changePart + "/" + crewId)
                                          .backUrl("/crew/show-crews")
                                          .csrfToken(token.getToken())
                                          .build());
    }

    private CrewChangeForm getPreviousAnswer(CrewPartDto changePart, Crew crew) {
        return new CrewChangeForm(
            switch (changePart) {
                case CREW_NAME -> List.of(crew.crewName());
                case CHARACTER_IDS -> crew.characterIds().isEmpty() ? Collections.emptyList() : crew.characterIds().stream().map(UUID::toString).toList();
                case LAIR -> crew.lair().map(List::of).orElse(Collections.emptyList());
                case LAIR_DETAILS -> crew.lairDetails().map(List::of).orElse(Collections.emptyList());
            });
    }

}
