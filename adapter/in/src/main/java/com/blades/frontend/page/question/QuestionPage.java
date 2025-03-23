package com.blades.frontend.page.question;

import com.blades.frontend.page.common.Page;

import org.springframework.util.ObjectUtils;

import java.util.List;

import lombok.Data;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "requiredBuilder")
public class QuestionPage extends Page {

    private final String action;
    @Singular private final List<Question> questions;
    private final String button;
    private final String csrfToken;

    public boolean hasOneQuestion() {
        return hasQuestions() && (questions.size() == 1);
    }

    public boolean hasQuestions() {
        return !ObjectUtils.isEmpty(questions);
    }

    public boolean hasErrors() {
        return !questionsWithErrors().isEmpty();
    }

    public List<Question> questionsWithErrors() {
        return questions.stream()
            .filter(Question::hasError)
            .toList();
    }

    public static QuestionPageBuilder<?, ?> builder(String groupStem) {
        return requiredBuilder()
            .templateName("question-page")
            .groupStem(groupStem);
    }

}
