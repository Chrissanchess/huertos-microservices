package mx.huertos.garden.dto;

import java.time.Instant;
import java.util.UUID;

// Refleja lo que devuelve parcel-service
public record ParcelDto(UUID id, String name, Double latitude, Double longitude,
                        String soilType, String ownerOrLead, Instant createdAt, String status) {}