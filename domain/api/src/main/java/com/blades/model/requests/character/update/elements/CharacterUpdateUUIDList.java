package com.blades.model.requests.character.update.elements;

import java.util.List;
import java.util.UUID;

public record CharacterUpdateUUIDList(List<UUID> changeElement)
    implements CharacterUpdateElement {

    @Override
    public List<UUID> getChangeElement() {
        return changeElement;
    }

}
