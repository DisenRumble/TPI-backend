package backend.grupo73.solicitudes_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitud_tramos_ejecucion")
public class TramoEjecucionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", nullable = false)
    private SolicitudModel solicitud;

    @Column(name = "origen_ubicacion_id", nullable = false)
    private UUID origenUbicacionId;

    @Column(name = "destino_ubicacion_id", nullable = false)
    private UUID destinoUbicacionId;

    @Column(name = "camion_id")
    private UUID camionId;

    @Column(name = "distancia_estimada_km")
    private Double distanciaEstimadaKm;

    @Column(name = "tiempo_estimado_min")
    private Integer tiempoEstimadoMin;

    @Column(name = "distancia_real_km")
    private Double distanciaRealKm;

    @Column(name = "tiempo_real_min")
    private Integer tiempoRealMin;

    @Column(name = "fecha_hora_inicio_real")
    private Instant fechaHoraInicioReal;

    @Column(name = "fecha_hora_fin_real")
    private Instant fechaHoraFinReal;

    @Column(name = "orden")
    private Integer orden;
}
