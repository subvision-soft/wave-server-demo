package fr.wave.remotedemo.transformer;

import fr.wave.remotedemo.dto.EnumDTO;

public class EnumTransformer {
    public static EnumDTO toDTO(Enum<?> e) {
        EnumDTO dto = new EnumDTO();
        dto.setLabel(e.toString());
        dto.setValue(e.name());
        return dto;
    }
}
