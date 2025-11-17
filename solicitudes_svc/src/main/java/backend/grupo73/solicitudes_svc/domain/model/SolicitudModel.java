package backend.grupo73.solicitudes_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "solicitudes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contenedor_id", nullable = false)
    private ContenedorModel contenedor;

    @Column(name = "origen_ubicacion_id", nullable = false)
    private UUID origenUbicacionId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "solicitud_depositos_intermedios", joinColumns = @JoinColumn(name = "solicitud_id"))
    @Column(name = "ubicacion_id")
    private List<UUID> depositosIntermediosUbicacionIds;

    @Column(name = "destino_ubicacion_id", nullable = false)
    private UUID destinoUbicacionId;
    
    @Column(name = "origen_lat")
    private Double origenLat;
    @Column(name = "origen_lng")
    private Double origenLng;
    @Column(name = "origen_descripcion")
    private String origenDescripcion;

    @Column(name = "destino_lat")
    private Double destinoLat;
    @Column(name = "destino_lng")
    private Double destinoLng;
    @Column(name = "destino_descripcion")
    private String destinoDescripcion;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("orden ASC")
    private List<TramoEjecucionModel> tramosEjecucion;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EstadiaDepositoModel> estadiasDeposito;

    @Column(name = "costo_estimado")
    private BigDecimal costoEstimado;

    @Column(name = "tiempo_estimado_minutos")
    private Integer tiempoEstimadoMinutos;

    @Column(name = "costo_real")
    private BigDecimal costoReal;

    @Column(name = "tiempo_real_minutos")
    private Integer tiempoRealMinutos;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
