package com.blades.data.common;

import com.blades.frontend.page.question.OptionValueDto;

import java.util.Collections;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class CheckboxForm <OPTION extends OptionValueDto> {

    @NotEmpty(message = "no.value.")
    List<OPTION> selected;

    public List<OPTION> selected() {
        return (selected == null) ? Collections.emptyList() : selected;
    }

}
