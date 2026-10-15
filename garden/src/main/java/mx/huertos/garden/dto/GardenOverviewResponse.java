package mx.huertos.garden.dto;

import java.util.List;

public record GardenOverviewResponse(ParcelDto parcel, List<String> recommendations) {}