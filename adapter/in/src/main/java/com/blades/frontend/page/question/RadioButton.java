package com.blades.frontend.page.question;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class RadioButton<T extends OptionValueDto> extends Question {

    private final T[] values;

    public List<OptionDto> options() {
        return Arrays.stream(values)
            .map(option -> new OptionDto(option.getValue(), option.getName(), getPreviousAnswers().contains(option.getValue())))
            .collect(Collectors.toList());
    }

}
