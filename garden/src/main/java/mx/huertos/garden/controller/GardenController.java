package mx.huertos.garden.controller;

import mx.huertos.garden.dto.GardenOverviewResponse;
import mx.huertos.garden.service.GardenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/garden/parcels")
public class GardenController {

    private final GardenService service;

    public GardenController(GardenService service) {
        this.service = service;
    }

    @GetMapping("/{id}/overview")
    public GardenOverviewResponse overview(@PathVariable UUID id) {
        return service.overview(id);
    }

    @GetMapping
    public List<GardenOverviewResponse> list(@RequestParam(required = false) String soilType) {
        return service.list(soilType);
    }
}