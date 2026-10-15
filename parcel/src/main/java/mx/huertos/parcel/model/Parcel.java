package mx.huertos.parcel.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "soil_type", nullable = false, length = 20)
    private SoilType soilType;

    @Column(name = "owner_or_lead", nullable = false, length = 100)
    private String ownerOrLead;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public SoilType getSoilType() { return soilType; }
    public void setSoilType(SoilType soilType) { this.soilType = soilType; }
    public String getOwnerOrLead() { return ownerOrLead; }
    public void setOwnerOrLead(String ownerOrLead) { this.ownerOrLead = ownerOrLead; }
    public Instant getCreatedAt() { return createdAt; }
}