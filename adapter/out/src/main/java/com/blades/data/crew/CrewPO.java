package com.blades.data.crew;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static java.util.Collections.emptyList;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(fluent = true)
public class CrewPO {

    @Id
    UUID crewId;
    String crewName;
    List<UUID> characterIds;
    String lair;
    String lairDetails;

    public List<UUID> getCharacterIds() {
        return (characterIds == null) ? emptyList() : characterIds;
    }

    public Optional<String> lair() {
        return Optional.ofNullable(lair);
    }

    public Optional<String> lairDetails() {
        return Optional.ofNullable(lairDetails);
    }

}
