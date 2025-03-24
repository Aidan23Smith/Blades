package com.blades.converter;

import com.blades.data.character.CharacterDto;
import com.blades.model.requests.character.CreateCharacterRequest;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RequestCharacterConverter {

    public CreateCharacterRequest toCharacterRequest(CharacterDto character, UUID owningUserId) {
        return new CreateCharacterRequest(owningUserId,
                                          character.name());
    }

}
