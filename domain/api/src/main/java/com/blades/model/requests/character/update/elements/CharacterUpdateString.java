package com.blades.model.requests.character.update.elements;

public record CharacterUpdateString(String changeElement)
    implements CharacterUpdateElement {

    @Override
    public String getChangeElement() {
        return changeElement;
    }

}
