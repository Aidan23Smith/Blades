package com.blades.model.requests.crew.update.elements;

import java.util.List;
import java.util.UUID;

public record CrewUpdateUUIDList(List<UUID> changeElement)
    implements CrewUpdateElement {

    @Override
    public List<UUID> getChangeElement() {
        return changeElement;
    }

}
