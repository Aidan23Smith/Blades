package com.blades.port.in;

import com.blades.model.character.Armour;
import com.blades.model.character.Harm;

import java.util.List;
import java.util.UUID;

public interface HarmService {

  void addHarm(UUID userId, UUID id, Harm harm);

  void rollForHealingClock(UUID userId, UUID id, int result);

  void updateArmour(UUID userId, UUID id, List<Armour> armour);

}
