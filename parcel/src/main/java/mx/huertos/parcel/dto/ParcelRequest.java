package mx.huertos.parcel.dto;

import jakarta.validation.constraints.*;
import mx.huertos.parcel.util.InputSanitizer;

public record ParcelRequest(

        @NotBlank(message = "El nombre de la parcela es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        @Pattern(regexp = "^[\\p{L}0-9 .,'-]*$",
                 message = "El nombre solo admite letras, números, espacios y . , ' -")
        String name,

        @NotNull(message = "La latitud es obligatoria")
        @DecimalMin(value = "-90.0", message = "La latitud debe ser mayor o igual a -90")
        @DecimalMax(value = "90.0", message = "La latitud debe ser menor o igual a 90")
        Double latitude,

        @NotNull(message = "La longitud es obligatoria")
        @DecimalMin(value = "-180.0", message = "La longitud debe ser mayor o igual a -180")
        @DecimalMax(value = "180.0", message = "La longitud debe ser menor o igual a 180")
        Double longitude,

        @NotBlank(message = "El tipo de suelo es obligatorio (arcilloso, limoso o arenoso)")
        String soilType,

        @NotBlank(message = "El responsable de la parcela es obligatorio")
        @Size(min = 3, max = 100, message = "El responsable debe tener entre 3 y 100 caracteres")
        @Pattern(regexp = "^[\\p{L} .'-]*$",
                 message = "El responsable solo admite letras, espacios y . ' -")
        String ownerOrLead
) {
    // Constructor compacto: sanitiza ANTES de que se valide
    public ParcelRequest {
        name = InputSanitizer.clean(name);
        soilType = InputSanitizer.clean(soilType);
        ownerOrLead = InputSanitizer.clean(ownerOrLead);
    }
}