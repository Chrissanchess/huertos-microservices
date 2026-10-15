package mx.huertos.parcel.service;

import mx.huertos.parcel.dto.ParcelRequest;
import mx.huertos.parcel.dto.ParcelResponse;
import mx.huertos.parcel.exception.DuplicateResourceException;
import mx.huertos.parcel.exception.ResourceNotFoundException;
import mx.huertos.parcel.model.Parcel;
import mx.huertos.parcel.model.SoilType;
import mx.huertos.parcel.repository.ParcelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ParcelService {

    private final ParcelRepository repository;

    public ParcelService(ParcelRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ParcelResponse create(ParcelRequest req) {
        SoilType soil = SoilType.from(req.soilType());   // lanza InvalidSoilTypeException

        if (repository.existsByNameIgnoreCase(req.name())) {
            throw new DuplicateResourceException(req.name());
        }

        Parcel p = new Parcel();
        p.setName(req.name());
        p.setLatitude(req.latitude());
        p.setLongitude(req.longitude());
        p.setSoilType(soil);
        p.setOwnerOrLead(req.ownerOrLead());
        return ParcelResponse.from(repository.save(p));
    }

    @Transactional(readOnly = true)
    public List<ParcelResponse> findAll() {
        return repository.findAll().stream().map(ParcelResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ParcelResponse findById(UUID id) {
        return repository.findById(id)
                .map(ParcelResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}