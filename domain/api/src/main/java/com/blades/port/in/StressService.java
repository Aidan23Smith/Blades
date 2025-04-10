package com.blades.port.in;

import com.blades.model.requests.character.TraumaRequest;

import java.util.UUID;

public interface StressService {

  void increaseStress(UUID userId, UUID id);

  void decreaseStress(UUID userId, UUID id);

  boolean isTooStressed(UUID userId, UUID id);

  void setNewTrauma(UUID userId, UUID id, TraumaRequest traumaRequest);

}
