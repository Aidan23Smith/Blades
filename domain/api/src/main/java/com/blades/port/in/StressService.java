package com.blades.port.in;

import com.blades.model.character.Trauma;

import java.util.UUID;

public interface StressService {

  void increaseStress(UUID userId, UUID id);

  void decreaseStress(UUID userId, UUID id);

  boolean isTooStressed(UUID userId, UUID id);

  void setNewTrauma(UUID userId, UUID id, Trauma trauma);

}
