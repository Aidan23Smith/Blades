package com.blades.model.requests.character;

import java.util.UUID;

public record UpdateCharacterRequest(UUID userId,
                                     UUID id,
                                     CharacterPartRequest characterPartRequest,
                                     String changeElement) {

}
