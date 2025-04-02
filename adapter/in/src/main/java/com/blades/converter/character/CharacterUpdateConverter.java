package com.blades.converter.character;

import com.blades.data.character.form.CharacterChangeForm;
import com.blades.model.requests.character.update.elements.CharacterUpdateElement;
import com.blades.model.requests.character.update.elements.CharacterUpdateString;
import com.blades.model.requests.character.update.elements.CharacterUpdateUUID;
import com.blades.model.requests.character.update.elements.CharacterUpdateUUIDList;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CharacterUpdateConverter {

    public CharacterUpdateElement toCharacterUpdateString(CharacterChangeForm characterChangeForm) {
        return new CharacterUpdateString(characterChangeForm.changeElement().stream().findFirst().orElse(null));
    }

    public CharacterUpdateElement toCharacterUpdateUUID(CharacterChangeForm characterChangeForm) {
        return new CharacterUpdateUUID(characterChangeForm.changeElement().stream().findFirst().map(UUID::fromString).orElse(null));
    }

    public CharacterUpdateElement toCharacterUpdateUUIDList(CharacterChangeForm characterChangeForm) {
        return new CharacterUpdateUUIDList(characterChangeForm.changeElement().stream().map(UUID::fromString).toList());
    }

}
