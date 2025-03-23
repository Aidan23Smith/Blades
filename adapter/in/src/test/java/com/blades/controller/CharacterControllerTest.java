package com.blades.controller;

import com.blades.converter.DisplayConverter;
import com.blades.converter.RequestCharacterConverter;
import com.blades.data.character.CharacterDto;
import com.blades.frontend.page.character.CharacterPage;
import com.blades.frontend.page.common.Page;
import com.blades.frontend.page.question.Input;
import com.blades.frontend.page.question.QuestionPage;
import com.blades.frontend.service.PageService;
import com.blades.model.CustomUser;
import com.blades.model.response.CharacterResponse;
import com.blades.port.in.CharacterInService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

import static com.blades.data.common.Navigation.CHARACTERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterControllerTest {

    @Mock
    private CharacterInService characterInService;
    @Mock
    private DisplayConverter displayConverter;
    @Mock
    private PageService pageService;
    @Mock
    private RequestCharacterConverter requestCharacterConverter;
    @InjectMocks
    private CharacterController underTest;

    @Mock
    CsrfToken csrfToken;
    @Mock
    private ModelAndView modelAndView;
    @Mock
    private Authentication authentication;
    @Mock
    private CustomUser customUser;
    @Mock
    private UUID userId;

    @Test
    void getCreateCharacterPage() {
        Page expected = QuestionPage.builder("character.create", CHARACTERS)
            .questions(List.of(
                Input.builder().questionId("name").build()
            ))
            .action("/create-character")
            .backUrl("/show-characters")
            .csrfToken(csrfToken.getToken())
            .build();

        when(pageService.createPage(expected)).thenReturn(modelAndView);

        assertEquals(underTest.getCreateCharacterPage(csrfToken), modelAndView);
        verify(csrfToken, times(2)).getToken();
        verifyNoInteractions(characterInService);
        verifyNoInteractions(requestCharacterConverter);
    }

    @Test
    void createCharacter() {

    }

    @Test
    void showCharacter() {
        List<CharacterDto> characters = mock(List.class);
        List<CharacterResponse> characterResponses = mock(List.class);
        when(authentication.getPrincipal()).thenReturn(customUser);
        when(customUser.getUserID()).thenReturn(userId);
        when(characterInService.getCharacters(userId)).thenReturn(characterResponses);
        when(displayConverter.toCharacterDtos(characterResponses)).thenReturn(characters);

        Page expected = CharacterPage.builder()
            .characters(characters)
            .build();

        when(pageService.createPage(expected)).thenReturn(modelAndView);

        assertEquals(underTest.showCharacter(authentication), modelAndView);
        verifyNoInteractions(requestCharacterConverter);
    }

    @Test
    void confirmDelete() {
    }

    @Test
    void deleteAndRedirect() {
    }

    @Test
    void changeDetails() {
    }

    @Test
    void editAndRedirect() {
    }
}
