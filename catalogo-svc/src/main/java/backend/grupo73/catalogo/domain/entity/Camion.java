package backend.grupo73.catalogo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "camiones")
public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String patente;

    private String descripcion;

    @Column(name = "capacidad_maxima_peso_kg", nullable = false)
    private BigDecimal capacidadMaximaPesoKg;

    @Column(name = "capacidad_maxima_volumen_m3", nullable = false)
    private BigDecimal capacidadMaximaVolumenM3;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCamion estado;

    private boolean activo = true;
}
