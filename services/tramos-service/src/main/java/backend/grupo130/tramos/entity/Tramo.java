package backend.grupo130.tramos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tramos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ruta_id")
    private Ruta ruta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_tramo_id")
    private TipoTramo tipoTramo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estado_tramo_id")
    private EstadoTramo estadoTramo;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    @Column(name = "ubicacion_origen_id", nullable = false)
    private UUID ubicacionOrigenId;

    @Column(name = "ubicacion_destino_id", nullable = false)
    private UUID ubicacionDestinoId;

    @Column(name = "deposito_id")
    private UUID depositoId;

    @Column(name = "patente_camion", length = 10)
    private String patenteCamion;

    @Column(name = "distancia_km")
    private Double distanciaKm;

    @Column(name = "tiempo_estimado_hs")
    private Integer tiempoEstimadoHoras;

    @Column(name = "tiempo_real_hs")
    private Integer tiempoRealHoras;

    @Column(name = "costo_estimado", precision = 15, scale = 2)
    private BigDecimal costoEstimado;

    @Column(name = "costo_real", precision = 15, scale = 2)
    private BigDecimal costoReal;

    @Column(name = "fecha_estimada_inicio")
    private LocalDateTime fechaEstimadaInicio;

    @Column(name = "fecha_estimada_fin")
    private LocalDateTime fechaEstimadaFin;

    @Column(name = "fecha_real_inicio")
    private LocalDateTime fechaRealInicio;

    @Column(name = "fecha_real_fin")
    private LocalDateTime fechaRealFin;
}
