package backend.grupo73.solicitudes_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "contenedores")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContenedorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "identificacion_unica", nullable = false, unique = true)
    private String identificacionUnica;

    @Column(nullable = false)
    private BigDecimal peso;

    @Column(nullable = false)
    private BigDecimal volumen;

    @Column(nullable = false)
    private String estado;

    @Column(name = "ubicacion_actual")
    private String ubicacionActual;

    // getters/setters
}
