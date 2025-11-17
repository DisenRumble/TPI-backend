package backend.grupo130.camiones.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "camiones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Camion {

    @Id
    @Column(length = 10)
    private String patente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportista_id")
    private Transportista transportista;

    @Column(name = "capacidad_volumen_m3", nullable = false, precision = 15, scale = 2)
    private BigDecimal capacidadVolumenM3;

    @Column(name = "capacidad_peso_kg", nullable = false, precision = 15, scale = 2)
    private BigDecimal capacidadPesoKg;

    @Column(name = "consumo_litros_km", nullable = false, precision = 15, scale = 2)
    private BigDecimal consumoLitrosKm;

    @Column(name = "costo_km", nullable = false, precision = 15, scale = 2)
    private BigDecimal costoKm;

    @Column(name = "anio_fabricacion")
    private Integer anioFabricacion;

    @Column(length = 40)
    private String tipo;

    @Column(name = "deposito_id")
    private UUID depositoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoCamion estado;

    @Column(name = "fecha_alta", nullable = false, updatable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @PrePersist
    void onCreate() {
        this.fechaAlta = LocalDateTime.now();
        this.fechaActualizacion = this.fechaAlta;
        if (estado == null) {
            estado = EstadoCamion.DISPONIBLE;
        }
    }

    @PreUpdate
    void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
