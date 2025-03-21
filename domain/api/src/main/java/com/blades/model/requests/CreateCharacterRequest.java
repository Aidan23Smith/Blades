package com.blades.model.requests;

import java.util.UUID;

public record CreateCharacterRequest(UUID owningUserId,
                                     String name) {

}
