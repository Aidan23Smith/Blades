package com.blades.data.character.form;

import com.blades.data.character.ArmourDto;
import com.blades.data.common.CheckboxForm;

import java.util.List;

public class ArmourForm extends CheckboxForm<ArmourDto> {

    public ArmourForm(List<ArmourDto> selected) {
        super(selected);
    }

}
