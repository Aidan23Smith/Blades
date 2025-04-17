package com.blades.converter.character;

import com.blades.data.character.ArmourDto;
import com.blades.data.character.CharacterDto;
import com.blades.data.character.form.HarmForm;
import com.blades.model.requests.character.ArmourRequest;
import com.blades.model.requests.character.CreateCharacterRequest;
import com.blades.model.requests.character.HarmRequest;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class RequestCharacterConverter {

    public CreateCharacterRequest toCharacterRequest(CharacterDto character, UUID owningUserId) {
        return new CreateCharacterRequest(owningUserId,
                                          character.name());
    }

    public HarmRequest toHarmRequest(HarmForm harmForm) {
        return new HarmRequest(harmForm.harmLevel().getLevel(), harmForm.harmDetail());
    }

    public List<ArmourRequest> toArmourRequest(List<ArmourDto> armour) {
        return armour.stream()
            .map(Enum::name)
            .map(ArmourRequest::valueOf)
            .toList();
    }

}
