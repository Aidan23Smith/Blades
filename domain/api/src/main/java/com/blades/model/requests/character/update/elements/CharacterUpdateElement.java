package com.blades.model.requests.character.update.elements;

import java.util.List;
import java.util.UUID;

public interface CharacterUpdateElement {

    Object getChangeElement();

    default String getString() {
        if (!(this instanceof CharacterUpdateString)) {
            throw new IllegalArgumentException("Update request is not a String");
        }
        return ((CharacterUpdateString) this).getChangeElement();
    }

    default UUID getUUID() {
        if (!(this instanceof CharacterUpdateUUID)) {
            throw new IllegalArgumentException("Update request is not a UUID");
        }
        return ((CharacterUpdateUUID) this).getChangeElement();
    }

    default List<UUID> getUUIDList() {
        if (!(this instanceof CharacterUpdateUUIDList)) {
            throw new IllegalArgumentException("Update request is not a List of UUIDs");
        }
        return ((CharacterUpdateUUIDList) this).getChangeElement();
    }

}
