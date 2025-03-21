package com.blades.usecase.converter;

import com.blades.model.requests.CharacterBackgroundRequest;
import com.blades.model.requests.CharacterHeritageRequest;
import com.blades.model.requests.CharacterTypeRequest;
import com.blades.model.requests.CharacterViceRequest;
import com.blades.model.requests.CreateCharacterRequest;
import com.blades.model.requests.SaveCharacterRequest;
import com.blades.model.response.CharacterBackgroundResponse;
import com.blades.model.response.CharacterHeritageResponse;
import com.blades.model.response.CharacterResponse;
import com.blades.model.response.CharacterTypeResponse;
import com.blades.model.response.CharacterViceResponse;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveCharacterConverterTest {

    private static final UUID OWNING_USER_ID = UUID.randomUUID();
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
    private static final SaveCharacterRequest EXPECTED_SAVE_CHARACTER_REQUEST = new SaveCharacterRequest(CHARACTER_ID,
                                                                                                         OWNING_USER_ID,
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


    private final SaveCharacterConverter converter = new SaveCharacterConverter();

    @Test
    void toSaveCharacterRequest_fromCharacterResponse() {
        CharacterResponse characterResponse = new CharacterResponse(CHARACTER_ID,
                                                                    OWNING_USER_ID,
                                                                    CHARACTER_NAME,
                                                                    CHARACTER_ALIAS,
                                                                    CharacterTypeResponse.valueOf(CHARACTER_TYPE_STRING),
                                                                    CREW_NAME,
                                                                    CHARACTER_LOOK,
                                                                    CharacterHeritageResponse.valueOf(CHARACTER_HERITAGE_STRING),
                                                                    CharacterBackgroundResponse.valueOf(CHARACTER_BACKGROUND_STRING),
                                                                    CHARACTER_BACKGROUND_DETAILS,
                                                                    CharacterViceResponse.valueOf(CHARACTER_VICE_STRING),
                                                                    CHARACTER_VICE_DETAILS);

        SaveCharacterRequest actual = converter.toSaveCharacterRequest(characterResponse);

        assertEquals(EXPECTED_SAVE_CHARACTER_REQUEST, actual);
    }

    @Test
    void toSaveCharacterRequest_fromCreateRequest() {
        CreateCharacterRequest createCharacterRequest = new CreateCharacterRequest(OWNING_USER_ID,
                                                                                   CHARACTER_NAME);

        try (MockedStatic<UUID> uuid = Mockito.mockStatic(UUID.class)) {
            uuid.when(UUID::randomUUID).thenReturn(CHARACTER_ID);
            SaveCharacterRequest actual = converter.toSaveCharacterRequest(createCharacterRequest);
            assertEquals(EXPECTED_SAVE_CHARACTER_REQUEST, actual);
        }
    }
}
