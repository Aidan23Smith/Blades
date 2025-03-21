package com.blades.frontend.page.question;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class RadioButton<T extends Enum<T>> extends Question {

    private final T[] values;

}
