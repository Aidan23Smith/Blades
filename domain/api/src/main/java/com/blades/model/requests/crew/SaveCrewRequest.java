package com.blades.model.requests.crew;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class SaveCrewRequest {

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
