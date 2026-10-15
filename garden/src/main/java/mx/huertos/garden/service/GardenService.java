package mx.huertos.garden.service;

import mx.huertos.garden.client.ParcelClient;
import mx.huertos.garden.dto.GardenOverviewResponse;
import mx.huertos.garden.dto.ParcelDto;
import mx.huertos.garden.model.SoilType;
import mx.huertos.garden.util.InputSanitizer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GardenService {

    private final ParcelClient parcelClient;

    public GardenService(ParcelClient parcelClient) {
        this.parcelClient = parcelClient;
    }

    public GardenOverviewResponse overview(UUID id) {
        return compose(parcelClient.getById(id));
    }

    public List<GardenOverviewResponse> list(String soilTypeFilter) {
        // Sanitiza y valida el filtro ANTES de llamar al otro servicio
        SoilType filter = null;
        String clean = InputSanitizer.clean(soilTypeFilter);
        if (clean != null && !clean.isEmpty()) {
            filter = SoilType.from(clean);
        }
        final SoilType f = filter;

        return parcelClient.getAll().stream()
                .filter(p -> f == null || p.soilType().equalsIgnoreCase(f.name()))
                .map(this::compose)
                .toList();
    }

    private GardenOverviewResponse compose(ParcelDto parcel) {
        List<String> recs = SoilType.from(parcel.soilType()).getRecommendations();
        return new GardenOverviewResponse(parcel, recs);
    }
}