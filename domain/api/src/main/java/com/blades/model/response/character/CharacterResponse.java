package com.blades.model.response.character;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Builder(toBuilder = true)
@Value
@Accessors(fluent = true)
@AllArgsConstructor
public class CharacterResponse {

    UUID id;
    UUID owningUserId;
    String name;
    String alias;
    CharacterTypeResponse type;
    UUID crewId;
    String look;
    CharacterHeritageResponse heritage;
    CharacterBackgroundResponse background;
    String backgroundDetails;
    CharacterViceResponse vice;
    String viceDetails;

    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    public Optional<CharacterTypeResponse> type() {
        return Optional.ofNullable(type);
    }

    public Optional<UUID> crewId() {
        return Optional.ofNullable(crewId);
    }

    public Optional<String> look() {
        return Optional.ofNullable(look);
    }

    public Optional<CharacterHeritageResponse> heritage() {
        return Optional.ofNullable(heritage);
    }

    public Optional<CharacterBackgroundResponse> background() {
        return Optional.ofNullable(background);
    }

    public Optional<String> backgroundDetails() {
        return Optional.ofNullable(backgroundDetails);
    }

    public Optional<CharacterViceResponse> vice() {
        return Optional.ofNullable(vice);
    }

    public Optional<String> viceDetails() {
        return Optional.ofNullable(viceDetails);
    }

}
