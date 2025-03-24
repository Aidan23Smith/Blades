package com.blades.usecase;

import com.blades.model.requests.character.CharacterBackgroundRequest;
import com.blades.model.requests.character.CharacterHeritageRequest;
import com.blades.model.requests.character.CharacterPartRequest;
import com.blades.model.requests.character.CharacterTypeRequest;
import com.blades.model.requests.character.CharacterViceRequest;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.SaveCharacterRequest;
import com.blades.model.requests.character.UpdateCharacterRequest;
import com.blades.model.response.character.CharacterResponse;
import com.blades.port.out.CharacterOutService;
import com.blades.usecase.converter.SaveCharacterConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterInServiceImplTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID CHARACTER_ID = UUID.randomUUID();
    private static final String CHARACTER_NAME = "Character Name";
    private static final String CHARACTER_ALIAS = "Character alias";
    private static final String CHARACTER_TYPE_STRING = "LURK";
    private static final String CREW_NAME = "Crew Name";
    private static final String CHARACTER_LOOK = "Character Look";
    private static final String CHARACTER_HERITAGE_STRING = "AKROS";
    private static final String CHARACTER_BACKGROUND_STRING = "ACADEMIC";
    private static final String CHARACTER_BACKGROUND_DETAILS = "Character Background Details";
    private static final String CHARACTER_VICE_STRING = "OBLIGATION";
    private static final String CHARACTER_VICE_DETAILS = "Character Service Details";

    @Mock
    private CharacterOutService characterOutService;
    @Mock
    private SaveCharacterConverter characterConverter;

    @InjectMocks
    private CharacterInServiceImpl underTest;

    @Test
    void createCharacter() {
        CreateCharacterRequest createCharacterRequest = mock(CreateCharacterRequest.class);
        SaveCharacterRequest saveCharacterRequest = mock(SaveCharacterRequest.class);
        when(characterConverter.toSaveCharacterRequest(createCharacterRequest)).thenReturn(saveCharacterRequest);

        underTest.createCharacter(createCharacterRequest);

        verify(characterOutService).saveCharacter(saveCharacterRequest);
    }

    @Nested
    class UpdateCharacter {

        private static final SaveCharacterRequest EXPECTED_REQUEST = new SaveCharacterRequest(USER_ID,
                                                                                              CHARACTER_ID,
                                                                                              CHARACTER_NAME,
                                                                                              CHARACTER_ALIAS,
                                                                                              CharacterTypeRequest.valueOf(CHARACTER_TYPE_STRING),
                                                                                              CREW_NAME,
                                                                                              CHARACTER_LOOK,
                                                                                              CharacterHeritageRequest.valueOf(CHARACTER_HERITAGE_STRING),
                                                                                              CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                                                              CHARACTER_BACKGROUND_DETAILS,
                                                                                              CharacterViceRequest.valueOf(CHARACTER_VICE_STRING),
                                                                                              CHARACTER_VICE_DETAILS);

        CharacterResponse characterResponse = mock(CharacterResponse.class);

        @BeforeEach
        void setUp() {
            when(characterOutService.getCharacter(USER_ID, CHARACTER_ID)).thenReturn(characterResponse);
        }

        @Test
        void updateCharacter_updateCharacterName() {
            when(characterConverter.toSaveCharacterRequest(characterResponse))
                .thenReturn(new SaveCharacterRequest(USER_ID,
                                                     CHARACTER_ID,
                                                     null,
                                                     CHARACTER_ALIAS,
                                                     CharacterTypeRequest.valueOf(CHARACTER_TYPE_STRING),
                                                     CREW_NAME,
                                                     CHARACTER_LOOK,
                                                     CharacterHeritageRequest.valueOf(CHARACTER_HERITAGE_STRING),
                                                     CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                     CHARACTER_BACKGROUND_DETAILS,
                                                     CharacterViceRequest.valueOf(CHARACTER_VICE_STRING),
                                                     CHARACTER_VICE_DETAILS));

            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
                                                                                       CHARACTER_ID,
                                                                                       CharacterPartRequest.NAME,
                                                                                       CHARACTER_NAME);

            underTest.updateCharacter(updateCharacterRequest);

            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
        }

        @Test
        void updateCharacter_updateCharacterAlias() {
            when(characterConverter.toSaveCharacterRequest(characterResponse))
                .thenReturn(new SaveCharacterRequest(USER_ID,
                                                     CHARACTER_ID,
                                                     CHARACTER_NAME,
                                                     null,
                                                     CharacterTypeRequest.valueOf(CHARACTER_TYPE_STRING),
                                                     CREW_NAME,
                                                     CHARACTER_LOOK,
                                                     CharacterHeritageRequest.valueOf(CHARACTER_HERITAGE_STRING),
                                                     CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                     CHARACTER_BACKGROUND_DETAILS,
                                                     CharacterViceRequest.valueOf(CHARACTER_VICE_STRING),
                                                     CHARACTER_VICE_DETAILS));

            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
                                                                                       CHARACTER_ID,
                                                                                       CharacterPartRequest.ALIAS,
                                                                                       CHARACTER_ALIAS);

            underTest.updateCharacter(updateCharacterRequest);

            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
        }

        @Test
        void updateCharacter_updateCharacterType() {
            when(characterConverter.toSaveCharacterRequest(characterResponse))
                .thenReturn(new SaveCharacterRequest(USER_ID,
                                                     CHARACTER_ID,
                                                     CHARACTER_NAME,
                                                     CHARACTER_ALIAS,
                                                     null,
                                                     CREW_NAME,
                                                     CHARACTER_LOOK,
                                                     CharacterHeritageRequest.valueOf(CHARACTER_HERITAGE_STRING),
                                                     CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                     CHARACTER_BACKGROUND_DETAILS,
                                                     CharacterViceRequest.valueOf(CHARACTER_VICE_STRING),
                                                     CHARACTER_VICE_DETAILS));

            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
                                                                                       CHARACTER_ID,
                                                                                       CharacterPartRequest.TYPE,
                                                                                       "LURK");

            underTest.updateCharacter(updateCharacterRequest);

            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
        }

        @Test
        void updateCharacter_updateCrewName() {
            when(characterConverter.toSaveCharacterRequest(characterResponse))
                .thenReturn(new SaveCharacterRequest(USER_ID,
                                                     CHARACTER_ID,
                                                     CHARACTER_NAME,
                                                     CHARACTER_ALIAS,
                                                     CharacterTypeRequest.valueOf(CHARACTER_TYPE_STRING),
                                                     null,
                                                     CHARACTER_LOOK,
                                                     CharacterHeritageRequest.valueOf(CHARACTER_HERITAGE_STRING),
                                                     CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                     CHARACTER_BACKGROUND_DETAILS,
                                                     CharacterViceRequest.valueOf(CHARACTER_VICE_STRING),
                                                     CHARACTER_VICE_DETAILS));

            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
                                                                                       CHARACTER_ID,
                                                                                       CharacterPartRequest.CREW_NAME,
                                                                                       CREW_NAME);

            underTest.updateCharacter(updateCharacterRequest);

            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
        }

        @Test
        void updateCharacter_updateCharacterLook() {
            when(characterConverter.toSaveCharacterRequest(characterResponse))
                .thenReturn(new SaveCharacterRequest(USER_ID,
                                                     CHARACTER_ID,
                                                     CHARACTER_NAME,
                                                     CHARACTER_ALIAS,
                                                     CharacterTypeRequest.valueOf(CHARACTER_TYPE_STRING),
                                                     CREW_NAME,
                                                     null,
                                                     CharacterHeritageRequest.valueOf(CHARACTER_HERITAGE_STRING),
                                                     CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                     CHARACTER_BACKGROUND_DETAILS,
                                                     CharacterViceRequest.valueOf(CHARACTER_VICE_STRING),
                                                     CHARACTER_VICE_DETAILS));

            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
                                                                                       CHARACTER_ID,
                                                                                       CharacterPartRequest.LOOK,
                                                                                       CHARACTER_LOOK);

            underTest.updateCharacter(updateCharacterRequest);

            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
        }

        @Test
        void updateCharacter_updateCharacterHeritage() {
            when(characterConverter.toSaveCharacterRequest(characterResponse))
                .thenReturn(new SaveCharacterRequest(USER_ID,
                                                     CHARACTER_ID,
                                                     CHARACTER_NAME,
                                                     CHARACTER_ALIAS,
                                                     CharacterTypeRequest.valueOf(CHARACTER_TYPE_STRING),
                                                     CREW_NAME,
                                                     CHARACTER_LOOK,
                                                     null,
                                                     CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                     CHARACTER_BACKGROUND_DETAILS,
                                                     CharacterViceRequest.valueOf(CHARACTER_VICE_STRING),
                                                     CHARACTER_VICE_DETAILS));

            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
                                                                                       CHARACTER_ID,
                                                                                       CharacterPartRequest.HERITAGE,
                                                                                       CHARACTER_HERITAGE_STRING);

            underTest.updateCharacter(updateCharacterRequest);

            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
        }

        @Test
        void updateCharacter_updateCharacterBackgroundDetails() {
            when(characterConverter.toSaveCharacterRequest(characterResponse))
                .thenReturn(new SaveCharacterRequest(USER_ID,
                                                     CHARACTER_ID,
                                                     CHARACTER_NAME,
                                                     CHARACTER_ALIAS,
                                                     CharacterTypeRequest.valueOf(CHARACTER_TYPE_STRING),
                                                     CREW_NAME,
                                                     CHARACTER_LOOK,
                                                     CharacterHeritageRequest.valueOf(CHARACTER_HERITAGE_STRING),
                                                     CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                     null,
                                                     CharacterViceRequest.valueOf(CHARACTER_VICE_STRING),
                                                     CHARACTER_VICE_DETAILS));

            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
                                                                                       CHARACTER_ID,
                                                                                       CharacterPartRequest.BACKGROUND_DETAILS,
                                                                                       CHARACTER_BACKGROUND_DETAILS);

            underTest.updateCharacter(updateCharacterRequest);

            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
        }

        @Test
        void updateCharacter_updateCharacterVice() {
            when(characterConverter.toSaveCharacterRequest(characterResponse))
                .thenReturn(new SaveCharacterRequest(USER_ID,
                                                     CHARACTER_ID,
                                                     CHARACTER_NAME,
                                                     CHARACTER_ALIAS,
                                                     CharacterTypeRequest.valueOf(CHARACTER_TYPE_STRING),
                                                     CREW_NAME,
                                                     CHARACTER_LOOK,
                                                     CharacterHeritageRequest.valueOf(CHARACTER_HERITAGE_STRING),
                                                     CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                     CHARACTER_BACKGROUND_DETAILS,
                                                     null,
                                                     CHARACTER_VICE_DETAILS));

            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
                                                                                       CHARACTER_ID,
                                                                                       CharacterPartRequest.VICE,
                                                                                       CHARACTER_VICE_STRING);

            underTest.updateCharacter(updateCharacterRequest);

            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
        }

        @Test
        void updateCharacter_updateCharacterViceDetails() {
            when(characterConverter.toSaveCharacterRequest(characterResponse))
                .thenReturn(new SaveCharacterRequest(USER_ID,
                                                     CHARACTER_ID,
                                                     CHARACTER_NAME,
                                                     CHARACTER_ALIAS,
                                                     CharacterTypeRequest.valueOf(CHARACTER_TYPE_STRING),
                                                     CREW_NAME,
                                                     CHARACTER_LOOK,
                                                     CharacterHeritageRequest.valueOf(CHARACTER_HERITAGE_STRING),
                                                     CharacterBackgroundRequest.valueOf(CHARACTER_BACKGROUND_STRING),
                                                     CHARACTER_BACKGROUND_DETAILS,
                                                     CharacterViceRequest.valueOf(CHARACTER_VICE_STRING),
                                                     CHARACTER_VICE_DETAILS));

            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
                                                                                       CHARACTER_ID,
                                                                                       CharacterPartRequest.VICE_DETAILS,
                                                                                       CHARACTER_VICE_DETAILS);

            underTest.updateCharacter(updateCharacterRequest);

            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
        }

    }

    @Test
    void getCharacters() {
        List<CharacterResponse> expected = mock(List.class);
        when(characterOutService.getCharacters(USER_ID)).thenReturn(expected);

        List<CharacterResponse> actual = underTest.getCharacters(USER_ID);

        assertEquals(expected, actual);
    }

    @Test
    void getCharacter() {
        CharacterResponse expected = mock(CharacterResponse.class);
        when(characterOutService.getCharacter(USER_ID, CHARACTER_ID)).thenReturn(expected);

        CharacterResponse actual = underTest.getCharacter(USER_ID, CHARACTER_ID);

        assertEquals(expected, actual);
    }

    @Test
    void deleteCharacter() {
        underTest.deleteCharacter(USER_ID, CHARACTER_ID);

        verify(characterOutService).deleteCharacter(USER_ID, CHARACTER_ID);
    }

}
