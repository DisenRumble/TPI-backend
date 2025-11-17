package backend.grupo73.solicitudes_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitud_estadias_deposito")
public class EstadiaDepositoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", nullable = false)
    private SolicitudModel solicitud;

    @Column(name = "deposito_ubicacion_id", nullable = false)
    private UUID depositoUbicacionId;

    @Column(name = "fecha_hora_entrada", nullable = false)
    private Instant fechaHoraEntrada;

    @Column(name = "fecha_hora_salida")
    private Instant fechaHoraSalida;

    @Column(name = "horas_estadia")
    private Long horasEstadia; // Puede calcularse o materializarse
}
