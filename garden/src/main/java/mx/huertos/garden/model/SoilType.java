package mx.huertos.garden.model;

import mx.huertos.garden.exception.InvalidSoilTypeException;
import java.util.Arrays;
import java.util.List;

public enum SoilType {
    ARCILLOSO(List.of(
            "Mejora el drenaje incorporando arena gruesa y composta",
            "Evita trabajar la tierra cuando esté muy mojada para no compactarla",
            "Prefiere cultivos de raíz poco profunda y rota con leguminosas")),
    LIMOSO(List.of(
            "Cubre el suelo con acolchado (mulch) para reducir la erosión",
            "Aplica composta cada temporada para mantener la estructura",
            "Evita el exceso de riego: se encharca con facilidad")),
    ARENOSO(List.of(
            "Aumenta la materia orgánica con composta para retener humedad y nutrientes",
            "Riega con más frecuencia pero en menor cantidad",
            "Usa abonos verdes y rota cultivos para recuperar nutrientes"));

    private final List<String> recommendations;

    SoilType(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public List<String> getRecommendations() { return recommendations; }

    public static SoilType from(String value) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new InvalidSoilTypeException(value));
    }
}