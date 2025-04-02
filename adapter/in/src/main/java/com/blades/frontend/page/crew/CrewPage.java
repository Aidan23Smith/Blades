package com.blades.frontend.page.crew;

import com.blades.data.common.Navigation;
import com.blades.data.crew.dto.CrewDto;
import com.blades.frontend.page.common.Page;

import java.util.List;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "requiredBuilder")
public class CrewPage extends Page {

    private final List<CrewDto> crews;

    public static CrewPageBuilder<?, ?> builder() {
        return requiredBuilder()
            .templateName("show-crews")
            .groupStem("crew")
            .currentPage(Navigation.CREWS);
    }

}
