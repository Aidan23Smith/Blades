package com.blades.model.requests.character;

import java.util.UUID;

public record CreateCharacterRequest(UUID owningUserId,
                                     String name) {

}
