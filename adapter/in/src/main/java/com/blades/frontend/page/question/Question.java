package com.blades.frontend.page.question;

import com.blades.data.error.ErrorDto;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(builderMethodName = "superBuilder")
public abstract class Question {

    private final String questionId;
    @Singular
    private final List<String> questionArgs;
    @Singular
    private final List<String> previousAnswers;
    private String errorProperty;

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

    public Question setError(Set<ErrorDto> allErrors) {
        if (allErrors == null) {
            return this;
        }
        errorProperty = allErrors.stream()
            .filter(error -> error.questionId().equals(questionId))
            .findFirst()
            .map(ErrorDto::error)
            .orElse(null);
        return this;
    }

}
