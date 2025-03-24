package com.blades.model.response.crew;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Builder
@Value
@Accessors(fluent = true)
@AllArgsConstructor
public class CrewResponse {

    UUID crewId;
    String crewName;
    List<UUID> characterIds;
    String lair;
    String lairDetails;

    public Optional<String> lair() {
        return Optional.ofNullable(lair);
    }

    public Optional<String> lairDetails() {
        return Optional.ofNullable(lairDetails);
    }

}
