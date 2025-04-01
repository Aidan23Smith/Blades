package com.blades.model.requests.crew.update.elements;

import java.util.List;
import java.util.UUID;

public interface CrewUpdateElement {

    Object getChangeElement();

    default String getString() {
        if (!(this instanceof CrewUpdateString)) {
            throw new IllegalArgumentException("Update request is not a String");
        }
        return ((CrewUpdateString) this).getChangeElement();
    }

    default List<UUID> getUUIDList() {
        if (!(this instanceof CrewUpdateUUIDList)) {
            throw new IllegalArgumentException("Update request is not a List of UUIDs");
        }
        return ((CrewUpdateUUIDList) this).getChangeElement();
    }

}
