package backend.grupo130.solicitudes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tarifas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Cargo fijo por solicitud que depende de la cantidad de tramos definidos.
     */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal cargoGestionPorTramo;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorPorKilometro;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorPorVolumen;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorCombustibleLitro;

    @Column(nullable = false)
    private Boolean vigente;
}
