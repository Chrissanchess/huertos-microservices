package mx.huertos.parcel.controller;

import jakarta.validation.Valid;
import mx.huertos.parcel.dto.ParcelRequest;
import mx.huertos.parcel.dto.ParcelResponse;
import mx.huertos.parcel.service.ParcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parcels")
public class ParcelController {

    private final ParcelService service;

    public ParcelController(ParcelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ParcelResponse> create(@Valid @RequestBody ParcelRequest request) {
        ParcelResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/parcels/" + created.id())).body(created);
    }

    @GetMapping
    public List<ParcelResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ParcelResponse detail(@PathVariable UUID id) {
        return service.findById(id);
    }
}