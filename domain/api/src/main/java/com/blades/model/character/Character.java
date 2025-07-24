package com.blades.model.character;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Accessors;

@Builder(toBuilder = true)
@Value
@Accessors(fluent = true)
@AllArgsConstructor
public class Character {

    UUID id;
    UUID owningUserId;
    String name;
    String alias;
    CharacterType type;
    UUID crewId;
    String look;
    CharacterHeritage heritage;
    CharacterBackground background;
    String backgroundDetails;
    CharacterVice vice;
    String viceDetails;
    int stress;
    @Singular
    List<Trauma> traumas;
    @Singular
    List<Harm> harms;
    int healingClock;
    @Singular
    List<Armour> armours;

    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    public Optional<CharacterType> type() {
        return Optional.ofNullable(type);
    }

    public Optional<UUID> crewId() {
        return Optional.ofNullable(crewId);
    }

    public Optional<String> look() {
        return Optional.ofNullable(look);
    }

    public Optional<CharacterHeritage> heritage() {
        return Optional.ofNullable(heritage);
    }

    public Optional<CharacterBackground> background() {
        return Optional.ofNullable(background);
    }

    public Optional<String> backgroundDetails() {
        return Optional.ofNullable(backgroundDetails);
    }

    public Optional<CharacterVice> vice() {
        return Optional.ofNullable(vice);
    }

    public Optional<String> viceDetails() {
        return Optional.ofNullable(viceDetails);
    }

}
