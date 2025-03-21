package com.blades.data.character;

import org.springframework.data.annotation.Id;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(fluent = true)
public class CharacterPO {

    @Id
    UUID id;
    UUID owningUserId;
    String name;
    String alias;
    CharacterTypePO type;
    String crewName;
    String look;
    CharacterHeritagePO heritage;
    CharacterBackgroundPO background;
    String backgroundDetails;
    CharacterVicePO vice;
    String viceDetails;

    public Optional<String> alias() {
        return Optional.ofNullable(alias);
    }

    public Optional<CharacterTypePO> type() {
        return Optional.ofNullable(type);
    }

    public Optional<String> crewName() {
        return Optional.ofNullable(crewName);
    }

    public Optional<String> look() {
        return Optional.ofNullable(look);
    }

    public Optional<CharacterHeritagePO> heritage() {
        return Optional.ofNullable(heritage);
    }

    public Optional<CharacterBackgroundPO> background() {
        return Optional.ofNullable(background);
    }

    public Optional<String> backgroundDetails() {
        return Optional.ofNullable(backgroundDetails);
    }

    public Optional<CharacterVicePO> vice() {
        return Optional.ofNullable(vice);
    }

    public Optional<String> viceDetails() {
        return Optional.ofNullable(viceDetails);
    }

}
