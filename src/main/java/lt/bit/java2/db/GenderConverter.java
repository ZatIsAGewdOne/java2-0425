package lt.bit.java2.db;

import javax.persistence.AttributeConverter;

/**
 * Naudoti taip: @Convert(converter = GenderConverter.class)
 *
 */
public class GenderConverter implements AttributeConverter<Gender, Character> {

    public Character convertToDatabaseColumn(Gender value) {
        if (value == null) {
            return null;
        }

        return value.getValue();
    }

    public Gender convertToEntityAttribute(Character value) {
        if (value == null) {
            return null;
        }

        return Gender.fromValue(value);
    }
}