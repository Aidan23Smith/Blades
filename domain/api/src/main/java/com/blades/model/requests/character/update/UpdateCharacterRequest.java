package com.blades.model.requests.character.update;

import com.blades.model.requests.character.CharacterPartRequest;
import com.blades.model.requests.character.update.elements.CharacterUpdateElement;

import java.util.UUID;

public record UpdateCharacterRequest(UUID userId,
                                     UUID id,
                                     CharacterPartRequest characterPartRequest,
                                     CharacterUpdateElement characterUpdateElement) {

}
