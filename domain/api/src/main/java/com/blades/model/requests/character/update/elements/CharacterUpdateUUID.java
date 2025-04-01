package com.blades.model.requests.character.update.elements;

import java.util.UUID;

public record CharacterUpdateUUID(UUID changeElement)
    implements CharacterUpdateElement {

    @Override
    public UUID getChangeElement() {
        return changeElement;
    }

}
