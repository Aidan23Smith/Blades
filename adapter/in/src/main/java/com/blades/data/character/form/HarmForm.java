package com.blades.data.character.form;

import com.blades.data.character.HarmLevelDto;

import jakarta.validation.constraints.NotNull;

public record HarmForm(@NotNull String harmDetail,
                       @NotNull HarmLevelDto harmLevel) {

}
