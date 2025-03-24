package com.blades.service;

import com.blades.converter.CharacterConverter;
import com.blades.dao.CharacterDao;
import com.blades.data.character.CharacterPO;
import com.blades.model.requests.character.SaveCharacterRequest;
import com.blades.model.response.character.CharacterResponse;

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
class CharacterOutServiceImplTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID CHARACTER_ID = UUID.randomUUID();

    @Mock
    private CharacterDao characterDao;
    @Mock
    private CharacterConverter characterConverter;

    @InjectMocks
    private CharacterOutServiceImpl underTest;

    @Test
    void saveCharacter() {
        SaveCharacterRequest saveCharacterRequest = mock(SaveCharacterRequest.class);
        CharacterPO characterPO = mock(CharacterPO.class);
        when(characterConverter.toCharacterPO(saveCharacterRequest)).thenReturn(characterPO);

        underTest.saveCharacter(saveCharacterRequest);

        verify(characterDao).save(characterPO);
    }

    @Test
    void getCharacters() {
        List<CharacterPO> characterPOs = mock(List.class);
        List<CharacterResponse> expected = mock(List.class);
        when(characterDao.findByOwningUserId(USER_ID)).thenReturn(characterPOs);
        when(characterConverter.toCharacterResponses(characterPOs)).thenReturn(expected);

        List<CharacterResponse> actual = underTest.getCharacters(USER_ID);

        assertEquals(expected, actual);
    }

    @Test
    void getCharacter() {
        CharacterPO characterPO = mock(CharacterPO.class);
        CharacterResponse expected = mock(CharacterResponse.class);
        when(characterDao.findByOwningUserIdAndId(USER_ID, CHARACTER_ID)).thenReturn(characterPO);
        when(characterConverter.toCharacterResponse(characterPO)).thenReturn(expected);

        CharacterResponse actual = underTest.getCharacter(USER_ID, CHARACTER_ID);

        assertEquals(expected, actual);
    }

    @Test
    void deleteCharacter() {
        underTest.deleteCharacter(USER_ID, CHARACTER_ID);

        verify(characterDao).deleteByOwningUserIdAndId(USER_ID, CHARACTER_ID);
    }

}
