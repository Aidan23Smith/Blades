package com.blades.converter;

import com.blades.dao.CrewDao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

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

    //todo
//    @Test
//    void toCharacterResponses() {
//        when(crewDao.findAll()).thenReturn(
//            List.of(
//                CrewPO.builder().characterIds(List.of(CHARACTER_ID_1)).crewId(CREW_ID_1).build(),
//                CrewPO.builder().characterIds(List.of(CHARACTER_ID_2)).crewId(CREW_ID_2).build()
//
//            )
//        );
//
//        List<CharacterPO> characters = List.of(new CharacterPO(CHARACTER_ID_1,
//                                                               USER_ID_1,
//                                                               CHARACTER_NAME_1,
//                                                               CHARACTER_ALIAS_1,
//                                                               CharacterTypePO.LURK,
//                                                               LOOK_1,
//                                                               CharacterHeritagePO.AKROS,
//                                                               CharacterBackgroundPO.ACADEMIC,
//                                                               BACKGROUND_DETAILS_1,
//                                                               CharacterVicePO.OBLIGATION,
//                                                               VICE_DETAILS_1),
//                                               new CharacterPO(CHARACTER_ID_2,
//                                                               USER_ID_2,
//                                                               CHARACTER_NAME_2,
//                                                               CHARACTER_ALIAS_2,
//                                                               CharacterTypePO.LURK,
//                                                               LOOK_2,
//                                                               CharacterHeritagePO.IRUVIA,
//                                                               CharacterBackgroundPO.LABOR,
//                                                               BACKGROUND_DETAILS_2,
//                                                               CharacterVicePO.FAITH,
//                                                               VICE_DETAILS_2));
//        List<Character> expected = List.of(new Character(CHARACTER_ID_1,
//                                                                         USER_ID_1,
//                                                                         CHARACTER_NAME_1,
//                                                                         CHARACTER_ALIAS_1,
//                                                                         CharacterType.LURK,
//                                                                         CREW_ID_1,
//                                                                         LOOK_1,
//                                                                         CharacterHeritage.AKROS,
//                                                                         CharacterBackground.ACADEMIC,
//                                                                         BACKGROUND_DETAILS_1,
//                                                                         CharacterVice.OBLIGATION,
//                                                                         VICE_DETAILS_1),
//                                                   new Character(CHARACTER_ID_2,
//                                                                         USER_ID_2,
//                                                                         CHARACTER_NAME_2,
//                                                                         CHARACTER_ALIAS_2,
//                                                                         CharacterType.LURK,
//                                                                         CREW_ID_2,
//                                                                         LOOK_2,
//                                                                         CharacterHeritage.IRUVIA,
//                                                                         CharacterBackground.LABOR,
//                                                                         BACKGROUND_DETAILS_2,
//                                                                         CharacterVice.FAITH,
//                                                                         VICE_DETAILS_2));
//
//        List<Character> actual = underTest.toCharacterResponses(characters);
//
//        assertEquals(expected, actual);
//    }
//todo
//    @Test
//    void toCharacterResponse() {
//        when(crewDao.findAll()).thenReturn(
//            List.of(
//                CrewPO.builder().characterIds(List.of(CHARACTER_ID_1)).crewId(CREW_ID_1).build(),
//                CrewPO.builder().characterIds(List.of(CHARACTER_ID_2)).crewId(CREW_ID_2).build()
//
//            )
//        );
//
//        CharacterPO characterPO = new CharacterPO(CHARACTER_ID_1,
//                                                  USER_ID_1,
//                                                  CHARACTER_NAME_1,
//                                                  CHARACTER_ALIAS_1,
//                                                  CharacterTypePO.LURK,
//                                                  LOOK_1,
//                                                  CharacterHeritagePO.AKROS,
//                                                  CharacterBackgroundPO.ACADEMIC,
//                                                  BACKGROUND_DETAILS_1,
//                                                  CharacterVicePO.OBLIGATION,
//                                                  VICE_DETAILS_1);
//        Character expected = new Character(CHARACTER_ID_1,
//                                                           USER_ID_1,
//                                                           CHARACTER_NAME_1,
//                                                           CHARACTER_ALIAS_1,
//                                                           CharacterType.LURK,
//                                                           CREW_ID_1,
//                                                           LOOK_1,
//                                                           CharacterHeritage.AKROS,
//                                                           CharacterBackground.ACADEMIC,
//                                                           BACKGROUND_DETAILS_1,
//                                                           CharacterVice.OBLIGATION,
//                                                           VICE_DETAILS_1);
//
//        Character actual = underTest.toCharacterResponse(characterPO);
//
//        assertEquals(expected, actual);
//    }

//    @Test
//    void toCharacterPO() {
//        SaveCharacterRequest saveCharacterRequest = new SaveCharacterRequest(CHARACTER_ID_1,
//                                                                             USER_ID_1,
//                                                                             CHARACTER_NAME_1,
//                                                                             CHARACTER_ALIAS_1,
//                                                                             CharacterType.LURK,
//                                                                             CREW_ID_1,
//                                                                             LOOK_1,
//                                                                             CharacterHeritage.AKROS,
//                                                                             CharacterBackground.ACADEMIC,
//                                                                             BACKGROUND_DETAILS_1,
//                                                                             CharacterVice.OBLIGATION,
//                                                                             VICE_DETAILS_1);
//
//        CharacterPO expected = new CharacterPO(CHARACTER_ID_1,
//                                               USER_ID_1,
//                                               CHARACTER_NAME_1,
//                                               CHARACTER_ALIAS_1,
//                                               CharacterTypePO.LURK,
//                                               LOOK_1,
//                                               CharacterHeritagePO.AKROS,
//                                               CharacterBackgroundPO.ACADEMIC,
//                                               BACKGROUND_DETAILS_1,
//                                               CharacterVicePO.OBLIGATION,
//                                               VICE_DETAILS_1);
//
//        CharacterPO actual = underTest.toCharacterPO(saveCharacterRequest);
//
//        assertEquals(expected, actual);
//    }

}
