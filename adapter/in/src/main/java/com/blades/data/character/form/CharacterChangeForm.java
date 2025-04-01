package com.blades.data.character.form;

import java.util.Collections;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record CharacterChangeForm(@NotEmpty(message = "no.value.") List<String> changeElement) {

    public CharacterChangeForm() {
        this(Collections.emptyList());
    }

    public String getSingleElement() {
        return changeElement.isEmpty() ? "" : changeElement.getFirst();
    }

}
