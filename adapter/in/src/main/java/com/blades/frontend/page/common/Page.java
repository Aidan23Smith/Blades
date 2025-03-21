package com.blades.frontend.page.common;

import java.util.List;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "requiredBuilder")
public class Page {

    private final String templateName;
    private final String backUrl;
    private final String groupStem;
    private final List<String> titleArgs;

    public static PageBuilder<?, ?> builder(String templateName,
                                      String groupStem) {
        return requiredBuilder().templateName(templateName).groupStem(groupStem);
    }

}
