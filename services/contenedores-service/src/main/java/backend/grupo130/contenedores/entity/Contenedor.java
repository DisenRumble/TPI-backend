package backend.grupo130.contenedores.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "contenedores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 40)
    private String identificador;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal pesoKg;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal volumenM3;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoContenedor estado;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(name = "solicitud_id")
    private UUID solicitudId;

    @Column(name = "deposito_id")
    private UUID depositoId;

    @Column(length = 250)
    private String descripcion;

    @Column(name = "fecha_alta", nullable = false, updatable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @PrePersist
    void onCreate() {
        this.fechaAlta = LocalDateTime.now();
        this.fechaActualizacion = this.fechaAlta;
        if (estado == null) {
            estado = EstadoContenedor.EN_ORIGEN;
        }
    }

    @PreUpdate
    void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
