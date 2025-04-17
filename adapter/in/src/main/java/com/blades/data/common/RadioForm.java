package com.blades.data.common;

import com.blades.frontend.page.question.OptionValueDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public abstract class RadioForm<OPTION extends OptionValueDto> {

    @NotNull(message = "no.value.")
    OPTION selected;

    public String selectedName() {
        return (selected == null) ? null : selected.getName();
    }

}
