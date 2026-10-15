package mx.huertos.parcel.dto;

import mx.huertos.parcel.model.Parcel;
import java.time.Instant;
import java.util.UUID;

public record ParcelResponse(
        UUID id,
        String name,
        Double latitude,
        Double longitude,
        String soilType,
        String ownerOrLead,
        Instant createdAt,
        String status
) {
    public static ParcelResponse from(Parcel p) {
        // "status" es fijo por ahora; con sensores se calcularía con sus lecturas
        return new ParcelResponse(p.getId(), p.getName(), p.getLatitude(), p.getLongitude(),
                p.getSoilType().name(), p.getOwnerOrLead(), p.getCreatedAt(), "SIN_LECTURAS");
    }
}