package com.blades.converter.crew;

import com.blades.data.crew.form.CrewChangeForm;
import com.blades.model.requests.crew.update.elements.CrewUpdateElement;
import com.blades.model.requests.crew.update.elements.CrewUpdateString;
import com.blades.model.requests.crew.update.elements.CrewUpdateUUIDList;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CrewUpdateConverter {

    public CrewUpdateElement toCrewUpdateString(CrewChangeForm crewChangeForm) {
        return new CrewUpdateString(crewChangeForm.changeElement().stream().findFirst().orElse(null));
    }

    public CrewUpdateElement toCrewUpdateUUIDList(CrewChangeForm crewChangeForm) {
        return new CrewUpdateUUIDList(crewChangeForm.changeElement().stream().map(UUID::fromString).toList());
    }

}
