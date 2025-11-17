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
@Table(name = "tarifas")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal tarifaPorKm;

    @Column(nullable = false)
    private BigDecimal tarifaPorKgKm;

    @Column(nullable = false)
    private BigDecimal tarifaEstadiaPorHora;

    private boolean activa = true;

    @Version
    private Long version;
}
