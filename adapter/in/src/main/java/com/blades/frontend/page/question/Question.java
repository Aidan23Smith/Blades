package com.blades.frontend.page.question;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@SuperBuilder(builderMethodName = "requiredBuilder")
public abstract class Question {

    private final String questionId;
    @Singular
    private final List<String> questionArgs;
    @Singular
    private final List<String> previousAnswers;
    private final String errorProperty;

    public boolean isInput() {
        return getClass().getSimpleName().equals("Input");
    }

    public boolean isRadioButton() {
        return getClass().getSimpleName().equals("RadioButton");
    }

    public boolean isCheckbox() {
        return getClass().getSimpleName().equals("Checkbox");
    }

    public boolean hasError() {
        return getErrorProperty() != null;
    }

    public String getPreviousAnswer() {
        return ((previousAnswers == null) || previousAnswers.isEmpty()) ? null : previousAnswers.getFirst();
    }

}
