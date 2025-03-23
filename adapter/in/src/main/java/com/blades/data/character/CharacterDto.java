package com.blades.data.character;

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
                           String viceDetails) {

}
