package com.blades.frontend.page.question;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Input extends Question {

    private final String type;

}
