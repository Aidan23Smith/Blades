package com.blades.model.requests.character;

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
public class SaveCharacterRequest {

    UUID id;
    UUID owningUserId;
    String name;
    String alias;
    CharacterTypeRequest type;
    String crewName;
    String look;
    CharacterHeritageRequest heritage;
    CharacterBackgroundRequest background;
    String backgroundDetails;
    CharacterViceRequest vice;
    String viceDetails;

    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    public Optional<CharacterTypeRequest> type() {
        return Optional.ofNullable(type);
    }

    public Optional<String> crewName() {
        return Optional.ofNullable(crewName);
    }

    public Optional<String> look() {
        return Optional.ofNullable(look);
    }

    public Optional<CharacterHeritageRequest> heritage() {
        return Optional.ofNullable(heritage);
    }

    public Optional<CharacterBackgroundRequest> background() {
        return Optional.ofNullable(background);
    }

    public Optional<String> backgroundDetails() {
        return Optional.ofNullable(backgroundDetails);
    }

    public Optional<CharacterViceRequest> vice() {
        return Optional.ofNullable(vice);
    }

    public Optional<String> viceDetails() {
        return Optional.ofNullable(viceDetails);
    }
}
