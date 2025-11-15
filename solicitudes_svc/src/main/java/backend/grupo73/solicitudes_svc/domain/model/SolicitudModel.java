package backend.grupo73.solicitudes_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
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

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private String destino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @Column(name = "ruta_id")
    private UUID rutaId;

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

    // getters/setters
}
