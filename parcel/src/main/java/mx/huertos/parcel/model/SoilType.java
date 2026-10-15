package mx.huertos.parcel.model;

import mx.huertos.parcel.exception.InvalidSoilTypeException;
import java.util.Arrays;

public enum SoilType {
    ARCILLOSO, LIMOSO, ARENOSO;

    public static SoilType from(String value) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new InvalidSoilTypeException(value));
    }
}