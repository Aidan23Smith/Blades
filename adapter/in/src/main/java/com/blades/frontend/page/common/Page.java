package com.blades.frontend.page.common;

import com.blades.data.common.Navigation;

import java.util.Arrays;
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
    private final Navigation currentPage;

    public static PageBuilder<?, ?> builder(String templateName,
                                            String groupStem,
                                            Navigation currentPage) {
        return requiredBuilder()
            .templateName(templateName)
            .groupStem(groupStem)
            .currentPage(currentPage);
    }

    public boolean hasNavigation() {
        return currentPage != null;
    }

    public List<NavigationDto> getNavigation() {
        return Arrays.stream(Navigation.values())
            .map(navigation -> new NavigationDto(navigation.name(),
                                                 navigation.getNavigationUrl(),
                                                 navigation == currentPage))
            .toList();
    }

}
