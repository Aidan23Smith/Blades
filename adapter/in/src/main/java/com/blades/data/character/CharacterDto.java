package com.blades.data.character;

import java.util.List;
import java.util.UUID;

public record CharacterDto(UUID owningUserId,
                           UUID id,
                           String name,
                           String alias,
                           CharacterTypeDto type,
                           String crewName,
                           String look,
                           CharacterHeritageDto heritage,
                           CharacterBackgroundDto background,
                           String backgroundDetails,
                           CharacterViceDto vice,
                           String viceDetails,
                           Integer stress,
                           List<TraumaDto> traumas,
                           List<HarmDto> harms,
                           Integer healingClock,
                           List<ArmourDto> armours) {

    public boolean hasTrauma() {
        return !traumas.isEmpty();
    }

    public boolean hasHarm() {
        return !harms.isEmpty();
    }

    public boolean hasArmour() {
        return !armours.isEmpty();
    }

}
