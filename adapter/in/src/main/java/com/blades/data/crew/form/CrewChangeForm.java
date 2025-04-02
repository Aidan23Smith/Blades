package com.blades.data.crew.form;

import java.util.Collections;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record CrewChangeForm(@NotEmpty(message = "no.value.") List<String> changeElement) {

    public CrewChangeForm() {
        this(Collections.emptyList());
    }

}
