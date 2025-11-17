package backend.grupo130.camiones.entity;

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
@Table(name = "transportistas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transportista {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 80)
    private String nombreCompleto;

    @Column(length = 30)
    private String telefono;

    @Column(length = 120)
    private String email;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(length = 30)
    private String licencia;

    @Column(precision = 15, scale = 2)
    private BigDecimal salario;

    @Column(nullable = false)
    private Boolean activo;
}
