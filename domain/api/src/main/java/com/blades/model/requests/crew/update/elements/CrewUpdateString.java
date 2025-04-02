package com.blades.model.requests.crew.update.elements;

public record CrewUpdateString(String changeElement)
    implements CrewUpdateElement {

    @Override
    public String getChangeElement() {
        return changeElement;
    }

}
