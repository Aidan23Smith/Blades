package com.blades.converter;

import com.blades.dao.CrewDao;
import com.blades.data.character.CharacterBackgroundPO;
import com.blades.data.character.CharacterHeritagePO;
import com.blades.data.character.CharacterPO;
import com.blades.data.character.CharacterTypePO;
import com.blades.data.character.CharacterVicePO;
import com.blades.data.crew.CrewPO;
import com.blades.model.requests.character.CharacterBackgroundRequest;
import com.blades.model.requests.character.CharacterHeritageRequest;
import com.blades.model.requests.character.CharacterTypeRequest;
import com.blades.model.requests.character.CharacterViceRequest;
import com.blades.model.requests.character.SaveCharacterRequest;
import com.blades.model.response.character.CharacterBackgroundResponse;
import com.blades.model.response.character.CharacterHeritageResponse;
import com.blades.model.response.character.CharacterResponse;
import com.blades.model.response.character.CharacterTypeResponse;
import com.blades.model.response.character.CharacterViceResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterConverterTest {

    private static final UUID USER_ID_1 = UUID.randomUUID();
    private static final UUID USER_ID_2 = UUID.randomUUID();
    private static final UUID CHARACTER_ID_1 = UUID.randomUUID();
    private static final UUID CHARACTER_ID_2 = UUID.randomUUID();
    private static final String CHARACTER_NAME_1 = "Character Name 1";
    private static final String CHARACTER_NAME_2 = "Character Name 2";
    private static final String CHARACTER_ALIAS_1 = "Character Alias 1";
    private static final String CHARACTER_ALIAS_2 = "Character Alias 2";
    private static final UUID CREW_ID_1 = UUID.randomUUID();
    private static final UUID CREW_ID_2 = UUID.randomUUID();
    private static final String LOOK_1 = "Look 1";
    private static final String LOOK_2 = "Look 2";
    private static final String BACKGROUND_DETAILS_1 = "Background Details 1";
    private static final String BACKGROUND_DETAILS_2 = "Background Details 2";
    private static final String VICE_DETAILS_1 = "Vice Details 1";
    private static final String VICE_DETAILS_2 = "Vice Details 2";

    @Mock
    private CrewDao crewDao;

    @InjectMocks
    private CharacterConverter underTest;

    @Test
    void toCharacterResponses() {
        when(crewDao.findAll()).thenReturn(
            List.of(
                CrewPO.builder().characterIds(List.of(CHARACTER_ID_1)).crewId(CREW_ID_1).build(),
                CrewPO.builder().characterIds(List.of(CHARACTER_ID_2)).crewId(CREW_ID_2).build()

            )
        );

        List<CharacterPO> characters = List.of(new CharacterPO(CHARACTER_ID_1,
                                                               USER_ID_1,
                                                               CHARACTER_NAME_1,
                                                               CHARACTER_ALIAS_1,
                                                               CharacterTypePO.LURK,
                                                               LOOK_1,
                                                               CharacterHeritagePO.AKROS,
                                                               CharacterBackgroundPO.ACADEMIC,
                                                               BACKGROUND_DETAILS_1,
                                                               CharacterVicePO.OBLIGATION,
                                                               VICE_DETAILS_1),
                                               new CharacterPO(CHARACTER_ID_2,
                                                               USER_ID_2,
                                                               CHARACTER_NAME_2,
                                                               CHARACTER_ALIAS_2,
                                                               CharacterTypePO.LURK,
                                                               LOOK_2,
                                                               CharacterHeritagePO.IRUVIA,
                                                               CharacterBackgroundPO.LABOR,
                                                               BACKGROUND_DETAILS_2,
                                                               CharacterVicePO.FAITH,
                                                               VICE_DETAILS_2));
        List<CharacterResponse> expected = List.of(new CharacterResponse(CHARACTER_ID_1,
                                                                         USER_ID_1,
                                                                         CHARACTER_NAME_1,
                                                                         CHARACTER_ALIAS_1,
                                                                         CharacterTypeResponse.LURK,
                                                                         CREW_ID_1,
                                                                         LOOK_1,
                                                                         CharacterHeritageResponse.AKROS,
                                                                         CharacterBackgroundResponse.ACADEMIC,
                                                                         BACKGROUND_DETAILS_1,
                                                                         CharacterViceResponse.OBLIGATION,
                                                                         VICE_DETAILS_1),
                                                   new CharacterResponse(CHARACTER_ID_2,
                                                                         USER_ID_2,
                                                                         CHARACTER_NAME_2,
                                                                         CHARACTER_ALIAS_2,
                                                                         CharacterTypeResponse.LURK,
                                                                         CREW_ID_2,
                                                                         LOOK_2,
                                                                         CharacterHeritageResponse.IRUVIA,
                                                                         CharacterBackgroundResponse.LABOR,
                                                                         BACKGROUND_DETAILS_2,
                                                                         CharacterViceResponse.FAITH,
                                                                         VICE_DETAILS_2));

        List<CharacterResponse> actual = underTest.toCharacterResponses(characters);

        assertEquals(expected, actual);
    }

    @Test
    void toCharacterResponse() {
        when(crewDao.findAll()).thenReturn(
            List.of(
                CrewPO.builder().characterIds(List.of(CHARACTER_ID_1)).crewId(CREW_ID_1).build(),
                CrewPO.builder().characterIds(List.of(CHARACTER_ID_2)).crewId(CREW_ID_2).build()

            )
        );

        CharacterPO characterPO = new CharacterPO(CHARACTER_ID_1,
                                                  USER_ID_1,
                                                  CHARACTER_NAME_1,
                                                  CHARACTER_ALIAS_1,
                                                  CharacterTypePO.LURK,
                                                  LOOK_1,
                                                  CharacterHeritagePO.AKROS,
                                                  CharacterBackgroundPO.ACADEMIC,
                                                  BACKGROUND_DETAILS_1,
                                                  CharacterVicePO.OBLIGATION,
                                                  VICE_DETAILS_1);
        CharacterResponse expected = new CharacterResponse(CHARACTER_ID_1,
                                                           USER_ID_1,
                                                           CHARACTER_NAME_1,
                                                           CHARACTER_ALIAS_1,
                                                           CharacterTypeResponse.LURK,
                                                           CREW_ID_1,
                                                           LOOK_1,
                                                           CharacterHeritageResponse.AKROS,
                                                           CharacterBackgroundResponse.ACADEMIC,
                                                           BACKGROUND_DETAILS_1,
                                                           CharacterViceResponse.OBLIGATION,
                                                           VICE_DETAILS_1);

        CharacterResponse actual = underTest.toCharacterResponse(characterPO);

        assertEquals(expected, actual);
    }

    @Test
    void toCharacterPO() {
        SaveCharacterRequest saveCharacterRequest = new SaveCharacterRequest(CHARACTER_ID_1,
                                                                             USER_ID_1,
                                                                             CHARACTER_NAME_1,
                                                                             CHARACTER_ALIAS_1,
                                                                             CharacterTypeRequest.LURK,
                                                                             CREW_ID_1,
                                                                             LOOK_1,
                                                                             CharacterHeritageRequest.AKROS,
                                                                             CharacterBackgroundRequest.ACADEMIC,
                                                                             BACKGROUND_DETAILS_1,
                                                                             CharacterViceRequest.OBLIGATION,
                                                                             VICE_DETAILS_1);

        CharacterPO expected = new CharacterPO(CHARACTER_ID_1,
                                               USER_ID_1,
                                               CHARACTER_NAME_1,
                                               CHARACTER_ALIAS_1,
                                               CharacterTypePO.LURK,
                                               LOOK_1,
                                               CharacterHeritagePO.AKROS,
                                               CharacterBackgroundPO.ACADEMIC,
                                               BACKGROUND_DETAILS_1,
                                               CharacterVicePO.OBLIGATION,
                                               VICE_DETAILS_1);

        CharacterPO actual = underTest.toCharacterPO(saveCharacterRequest);

        assertEquals(expected, actual);
    }

}
