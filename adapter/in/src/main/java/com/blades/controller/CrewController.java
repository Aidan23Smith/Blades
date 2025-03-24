package com.blades.controller;

import com.blades.converter.CrewDisplayConverter;
import com.blades.converter.RequestCrewConverter;
import com.blades.data.crew.CrewDto;
import com.blades.data.crew.CrewPartDto;
import com.blades.frontend.page.crew.CrewPage;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.Question;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.service.PageService;
import com.blades.model.requests.crew.CreateCrewRequest;
import com.blades.model.requests.crew.CrewPartRequest;
import com.blades.model.requests.crew.UpdateCrewRequest;
import com.blades.model.response.crew.CrewResponse;
import com.blades.port.in.CrewInService;

import org.springframework.http.MediaType;
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

import static com.blades.data.common.Navigation.CREWS;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CrewController {

    private final CrewInService crewInService;
    private final PageService pageService;
    private final RequestCrewConverter requestCrewConverter;
    private final CrewDisplayConverter crewDisplayConverter;

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
        CreateCrewRequest createCrewRequest = requestCrewConverter
            .toCrewRequest(crew);
        crewInService.createCrew(createCrewRequest);
        response.sendRedirect("/crew/show-crews");
    }

    @GetMapping("/crew/show-crews")
    public ModelAndView showCrew() {
        List<CrewResponse> crewResponses = crewInService.getCrews();
        return pageService.createPage(CrewPage.builder()
                                          .crews(crewDisplayConverter.toCrewDtos(crewResponses))
                                          .build());
    }

    @GetMapping("/crew/delete/{crewId}")
    public ModelAndView confirmDelete(@PathVariable UUID crewId,
                                      CsrfToken token) {
        CrewResponse crewResponse = crewInService.getCrew(crewId);
        return pageService.createPage(QuestionPage.builder("crew.delete", CREWS)
                                          .titleArgs(List.of(crewResponse.crewName()))
                                          .backUrl("/crew/show-crews")
                                          .action("/crew/delete/" + crewId)
                                          .csrfToken(token.getToken())
                                          .build());
    }

    @PostMapping("/crew/delete/{crewId}")
    public void deleteAndRedirect(@PathVariable UUID crewId,
                                  HttpServletResponse response) throws IOException {
        crewInService.deleteCrew(crewId);
        response.sendRedirect("/crew/show-crews");
    }

    @GetMapping("/crew/change/{changePart}/{crewId}")
    public ModelAndView changeDetails(@PathVariable CrewPartDto changePart,
                                      @PathVariable UUID crewId,
                                      String previousAnswer,
                                      String errorProperty,
                                      CsrfToken token) {
        CrewResponse crewResponse = crewInService.getCrew(crewId);
        previousAnswer = (previousAnswer == null) ? getPreviousAnswer(changePart, crewResponse) : previousAnswer;

        Question.QuestionBuilder builder = switch (changePart) {
            case CREW_NAME, LAIR, LAIR_DETAILS -> Input.builder();
            case CHARACTER_IDS -> Input.builder();
        };

        builder.questionId("changeElement")
            .questionArg(crewResponse.crewName())
            .questionArg("crew.change." + changePart)
            .previousAnswer(previousAnswer)
            .errorProperty(errorProperty)
            .build();
        return pageService.createPage(QuestionPage.builder("crew.change", CREWS)
                                          .question(builder.build())
                                          .action("/crew/change/" + changePart + "/" + crewId)
                                          .backUrl("/crew/show-crews")
                                          .csrfToken(token.getToken())
                                          .build());
    }

    @PostMapping("/crew/change/{changePart}/{userId}")
    public ModelAndView editAndRedirect(@PathVariable CrewPartDto changePart,
                                        @PathVariable UUID userId,
                                        @RequestParam(required = false) String changeElement,
                                        HttpServletResponse response,
                                        CsrfToken token) throws IOException {
        if ((changeElement == null) || changeElement.isEmpty()) {
            return changeDetails(changePart,
                                 userId,
                                 "",
                                 "no.value." + changePart,
                                 token);
        }

        crewInService.updateCrew(new UpdateCrewRequest(userId,
                                                       CrewPartRequest.valueOf(changePart.name()),
                                                       changeElement));
        response.sendRedirect("/crew/show-crews");
        return null;
    }

    private String getPreviousAnswer(CrewPartDto changePart, CrewResponse crewResponse) {
        return switch (changePart) {
            case CREW_NAME -> crewResponse.crewName();
            case CHARACTER_IDS -> crewResponse.characterIds().toString();
            case LAIR -> crewResponse.lair().orElse(null);
            case LAIR_DETAILS -> crewResponse.lairDetails().orElse(null);
        };
    }

}
