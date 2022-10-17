package com.licon.redis.core.type;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MfaConverter implements AttributeConverter<MfaType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MfaType mfaType) {
        if (mfaType == null){
            return null;
        }
        return mfaType.getCode();
    }

    @Override
    public MfaType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return MfaType.valueOf(dbData);
    }
}
