package com.blades.usecase;

import com.blades.model.character.Character;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.update.elements.CharacterUpdateElement;
import com.blades.model.requests.character.update.elements.CharacterUpdateString;
import com.blades.model.requests.character.update.elements.CharacterUpdateUUID;
import com.blades.port.out.CharacterOutService;

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
    private static final CharacterUpdateElement CHARACTER_NAME = new CharacterUpdateString("Character Name");
    private static final CharacterUpdateElement CHARACTER_ALIAS =  new CharacterUpdateString("Character alias");
    private static final CharacterUpdateElement CHARACTER_TYPE_STRING =  new CharacterUpdateString("LURK");
    private static final CharacterUpdateElement CREW_ID =  new CharacterUpdateUUID(UUID.randomUUID());
    private static final CharacterUpdateElement CHARACTER_LOOK =  new CharacterUpdateString("Character Look");
    private static final CharacterUpdateElement CHARACTER_HERITAGE_STRING =  new CharacterUpdateString("AKROS");
    private static final CharacterUpdateElement CHARACTER_BACKGROUND_STRING =  new CharacterUpdateString("ACADEMIC");
    private static final CharacterUpdateElement CHARACTER_BACKGROUND_DETAILS =  new CharacterUpdateString("Character Background Details");
    private static final CharacterUpdateElement CHARACTER_VICE_STRING =  new CharacterUpdateString("OBLIGATION");
    private static final CharacterUpdateElement CHARACTER_VICE_DETAILS =  new CharacterUpdateString("Character Service Details");

    @Mock
    private CharacterOutService characterOutService;

    @InjectMocks
    private CharacterInServiceImpl underTest;

    @Test
    void createCharacter() {
        CreateCharacterRequest createCharacterRequest = mock(CreateCharacterRequest.class);
        Character character = mock(Character.class);

        underTest.createCharacter(createCharacterRequest);

        verify(characterOutService).saveCharacter(character);
    }
//todo
//    @Nested
//    class UpdateCharacter {
//
//        private static final SaveCharacterRequest EXPECTED_REQUEST = new SaveCharacterRequest(USER_ID,
//                                                                                              CHARACTER_ID,
//                                                                                              CHARACTER_NAME.getString(),
//                                                                                              CHARACTER_ALIAS.getString(),
//                                                                                              CharacterType.valueOf(CHARACTER_TYPE_STRING.getString()),
//                                                                                              CREW_ID.getUUID(),
//                                                                                              CHARACTER_LOOK.getString(),
//                                                                                              CharacterHeritage.valueOf(CHARACTER_HERITAGE_STRING.getString()),
//                                                                                              CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                                                              CHARACTER_BACKGROUND_DETAILS.getString(),
//                                                                                              CharacterVice.valueOf(CHARACTER_VICE_STRING.getString()),
//                                                                                              CHARACTER_VICE_DETAILS.getString());
//
//        Character characterResponse = mock(Character.class);
//
//        @BeforeEach
//        void setUp() {
//            when(characterOutService.getCharacter(USER_ID, CHARACTER_ID)).thenReturn(characterResponse);
//        }
//
//        @Test
//        void updateCharacter_updateCharacterName() {
//            when(characterConverter.toSaveCharacterRequest(characterResponse))
//                .thenReturn(new SaveCharacterRequest(USER_ID,
//                                                     CHARACTER_ID,
//                                                     null,
//                                                     CHARACTER_ALIAS.getString(),
//                                                     CharacterType.valueOf(CHARACTER_TYPE_STRING.getString()),
//                                                     CREW_ID.getUUID(),
//                                                     CHARACTER_LOOK.getString(),
//                                                     CharacterHeritage.valueOf(CHARACTER_HERITAGE_STRING.getString()),
//                                                     CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                     CHARACTER_BACKGROUND_DETAILS.getString(),
//                                                     CharacterVice.valueOf(CHARACTER_VICE_STRING.getString()),
//                                                     CHARACTER_VICE_DETAILS.getString()));
//
//            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
//                                                                                       CHARACTER_ID,
//                                                                                       CharacterPartRequest.NAME,
//                                                                                       CHARACTER_NAME);
//
//            underTest.updateCharacter(updateCharacterRequest);
//
//            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
//        }
//
//        @Test
//        void updateCharacter_updateCharacterAlias() {
//            when(characterConverter.toSaveCharacterRequest(characterResponse))
//                .thenReturn(new SaveCharacterRequest(USER_ID,
//                                                     CHARACTER_ID,
//                                                     CHARACTER_NAME.getString(),
//                                                     null,
//                                                     CharacterType.valueOf(CHARACTER_TYPE_STRING.getString()),
//                                                     CREW_ID.getUUID(),
//                                                     CHARACTER_LOOK.getString(),
//                                                     CharacterHeritage.valueOf(CHARACTER_HERITAGE_STRING.getString()),
//                                                     CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                     CHARACTER_BACKGROUND_DETAILS.getString(),
//                                                     CharacterVice.valueOf(CHARACTER_VICE_STRING.getString()),
//                                                     CHARACTER_VICE_DETAILS.getString()));
//
//            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
//                                                                                       CHARACTER_ID,
//                                                                                       CharacterPartRequest.ALIAS,
//                                                                                       CHARACTER_ALIAS);
//
//            underTest.updateCharacter(updateCharacterRequest);
//
//            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
//        }
//
//        @Test
//        void updateCharacter_updateCharacterType() {
//            when(characterConverter.toSaveCharacterRequest(characterResponse))
//                .thenReturn(new SaveCharacterRequest(USER_ID,
//                                                     CHARACTER_ID,
//                                                     CHARACTER_NAME.getString(),
//                                                     CHARACTER_ALIAS.getString(),
//                                                     null,
//                                                     CREW_ID.getUUID(),
//                                                     CHARACTER_LOOK.getString(),
//                                                     CharacterHeritage.valueOf(CHARACTER_HERITAGE_STRING.getString()),
//                                                     CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                     CHARACTER_BACKGROUND_DETAILS.getString(),
//                                                     CharacterVice.valueOf(CHARACTER_VICE_STRING.getString()),
//                                                     CHARACTER_VICE_DETAILS.getString()));
//
//            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
//                                                                                       CHARACTER_ID,
//                                                                                       CharacterPartRequest.TYPE,
//                                                                                       new CharacterUpdateString(CHARACTER_TYPE_STRING.getString()));
//
//            underTest.updateCharacter(updateCharacterRequest);
//
//            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
//        }
//
//        @Test
//        void updateCharacter_updateCrewName() {
//            when(characterConverter.toSaveCharacterRequest(characterResponse))
//                .thenReturn(new SaveCharacterRequest(USER_ID,
//                                                     CHARACTER_ID,
//                                                     CHARACTER_NAME.getString(),
//                                                     CHARACTER_ALIAS.getString(),
//                                                     CharacterType.valueOf(CHARACTER_TYPE_STRING.getString()),
//                                                     null,
//                                                     CHARACTER_LOOK.getString(),
//                                                     CharacterHeritage.valueOf(CHARACTER_HERITAGE_STRING.getString()),
//                                                     CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                     CHARACTER_BACKGROUND_DETAILS.getString(),
//                                                     CharacterVice.valueOf(CHARACTER_VICE_STRING.getString()),
//                                                     CHARACTER_VICE_DETAILS.getString()));
//
//            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
//                                                                                       CHARACTER_ID,
//                                                                                       CharacterPartRequest.CREW_NAME,
//                                                                                       CREW_ID);
//
//            underTest.updateCharacter(updateCharacterRequest);
//
//            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
//        }
//
//        @Test
//        void updateCharacter_updateCharacterLook() {
//            when(characterConverter.toSaveCharacterRequest(characterResponse))
//                .thenReturn(new SaveCharacterRequest(USER_ID,
//                                                     CHARACTER_ID,
//                                                     CHARACTER_NAME.getString(),
//                                                     CHARACTER_ALIAS.getString(),
//                                                     CharacterType.valueOf(CHARACTER_TYPE_STRING.getString()),
//                                                     CREW_ID.getUUID(),
//                                                     null,
//                                                     CharacterHeritage.valueOf(CHARACTER_HERITAGE_STRING.getString()),
//                                                     CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                     CHARACTER_BACKGROUND_DETAILS.getString(),
//                                                     CharacterVice.valueOf(CHARACTER_VICE_STRING.getString()),
//                                                     CHARACTER_VICE_DETAILS.getString()));
//
//            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
//                                                                                       CHARACTER_ID,
//                                                                                       CharacterPartRequest.LOOK,
//                                                                                       CHARACTER_LOOK);
//
//            underTest.updateCharacter(updateCharacterRequest);
//
//            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
//        }
//
//        @Test
//        void updateCharacter_updateCharacterHeritage() {
//            when(characterConverter.toSaveCharacterRequest(characterResponse))
//                .thenReturn(new SaveCharacterRequest(USER_ID,
//                                                     CHARACTER_ID,
//                                                     CHARACTER_NAME.getString(),
//                                                     CHARACTER_ALIAS.getString(),
//                                                     CharacterType.valueOf(CHARACTER_TYPE_STRING.getString()),
//                                                     CREW_ID.getUUID(),
//                                                     CHARACTER_LOOK.getString(),
//                                                     null,
//                                                     CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                     CHARACTER_BACKGROUND_DETAILS.getString(),
//                                                     CharacterVice.valueOf(CHARACTER_VICE_STRING.getString()),
//                                                     CHARACTER_VICE_DETAILS.getString()));
//
//            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
//                                                                                       CHARACTER_ID,
//                                                                                       CharacterPartRequest.HERITAGE,
//                                                                                       CHARACTER_HERITAGE_STRING);
//
//            underTest.updateCharacter(updateCharacterRequest);
//
//            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
//        }
//
//        @Test
//        void updateCharacter_updateCharacterBackgroundDetails() {
//            when(characterConverter.toSaveCharacterRequest(characterResponse))
//                .thenReturn(new SaveCharacterRequest(USER_ID,
//                                                     CHARACTER_ID,
//                                                     CHARACTER_NAME.getString(),
//                                                     CHARACTER_ALIAS.getString(),
//                                                     CharacterType.valueOf(CHARACTER_TYPE_STRING.getString()),
//                                                     CREW_ID.getUUID(),
//                                                     CHARACTER_LOOK.getString(),
//                                                     CharacterHeritage.valueOf(CHARACTER_HERITAGE_STRING.getString()),
//                                                     CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                     null,
//                                                     CharacterVice.valueOf(CHARACTER_VICE_STRING.getString()),
//                                                     CHARACTER_VICE_DETAILS.getString()));
//
//            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
//                                                                                       CHARACTER_ID,
//                                                                                       CharacterPartRequest.BACKGROUND_DETAILS,
//                                                                                       CHARACTER_BACKGROUND_DETAILS);
//
//            underTest.updateCharacter(updateCharacterRequest);
//
//            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
//        }
//
//        @Test
//        void updateCharacter_updateCharacterVice() {
//            when(characterConverter.toSaveCharacterRequest(characterResponse))
//                .thenReturn(new SaveCharacterRequest(USER_ID,
//                                                     CHARACTER_ID,
//                                                     CHARACTER_NAME.getString(),
//                                                     CHARACTER_ALIAS.getString(),
//                                                     CharacterType.valueOf(CHARACTER_TYPE_STRING.getString()),
//                                                     CREW_ID.getUUID(),
//                                                     CHARACTER_LOOK.getString(),
//                                                     CharacterHeritage.valueOf(CHARACTER_HERITAGE_STRING.getString()),
//                                                     CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                     CHARACTER_BACKGROUND_DETAILS.getString(),
//                                                     null,
//                                                     CHARACTER_VICE_DETAILS.getString()));
//
//            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
//                                                                                       CHARACTER_ID,
//                                                                                       CharacterPartRequest.VICE,
//                                                                                       CHARACTER_VICE_STRING);
//
//            underTest.updateCharacter(updateCharacterRequest);
//
//            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
//        }
//
//        @Test
//        void updateCharacter_updateCharacterViceDetails() {
//            when(characterConverter.toSaveCharacterRequest(characterResponse))
//                .thenReturn(new SaveCharacterRequest(USER_ID,
//                                                     CHARACTER_ID,
//                                                     CHARACTER_NAME.getString(),
//                                                     CHARACTER_ALIAS.getString(),
//                                                     CharacterType.valueOf(CHARACTER_TYPE_STRING.getString()),
//                                                     CREW_ID.getUUID(),
//                                                     CHARACTER_LOOK.getString(),
//                                                     CharacterHeritage.valueOf(CHARACTER_HERITAGE_STRING.getString()),
//                                                     CharacterBackground.valueOf(CHARACTER_BACKGROUND_STRING.getString()),
//                                                     CHARACTER_BACKGROUND_DETAILS.getString(),
//                                                     CharacterVice.valueOf(CHARACTER_VICE_STRING.getString()),
//                                                     CHARACTER_VICE_DETAILS.getString()));
//
//            UpdateCharacterRequest updateCharacterRequest = new UpdateCharacterRequest(USER_ID,
//                                                                                       CHARACTER_ID,
//                                                                                       CharacterPartRequest.VICE_DETAILS,
//                                                                                       CHARACTER_VICE_DETAILS);
//
//            underTest.updateCharacter(updateCharacterRequest);
//
//            verify(characterOutService).saveCharacter(EXPECTED_REQUEST);
//        }
//
//    }

    @Test
    void getCharacters() {
        List<Character> expected = mock(List.class);
        when(characterOutService.getCharacters(USER_ID)).thenReturn(expected);

        List<Character> actual = underTest.getCharacters(USER_ID);

        assertEquals(expected, actual);
    }

    @Test
    void getCharacter() {
        Character expected = mock(Character.class);
        when(characterOutService.getCharacter(USER_ID, CHARACTER_ID)).thenReturn(expected);

        Character actual = underTest.getCharacter(USER_ID, CHARACTER_ID);

        assertEquals(expected, actual);
    }

    @Test
    void deleteCharacter() {
        underTest.deleteCharacter(USER_ID, CHARACTER_ID);

        verify(characterOutService).deleteCharacter(USER_ID, CHARACTER_ID);
    }

}
