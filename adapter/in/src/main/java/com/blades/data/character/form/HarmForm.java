package com.blades.data.character.form;

import com.blades.data.character.HarmLevelDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record HarmForm(@NotEmpty String harmDetail,
                       @NotNull HarmLevelDto harmLevel) {

}
