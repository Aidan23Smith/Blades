package com.blades.data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RollForm(@NotNull @Min(1) @Max(6) Integer roll) {

}
