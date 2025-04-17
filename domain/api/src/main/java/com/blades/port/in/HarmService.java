package com.blades.port.in;

import com.blades.model.requests.character.ArmourRequest;
import com.blades.model.requests.character.HarmRequest;

import java.util.List;
import java.util.UUID;

public interface HarmService {

  void addHarm(UUID userId, UUID id, HarmRequest harmRequest);

  void rollForHealingClock(UUID userId, UUID id, int result);

  void updateArmour(UUID userId, UUID id, List<ArmourRequest> armourRequest);

}
