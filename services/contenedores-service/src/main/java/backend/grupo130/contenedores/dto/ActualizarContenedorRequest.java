package backend.grupo130.contenedores.dto;

import backend.grupo130.contenedores.entity.EstadoContenedor;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.UUID;

public record ActualizarContenedorRequest(
    BigDecimal pesoKg,
    BigDecimal volumenM3,
    EstadoContenedor estado,
    UUID clienteId,
    UUID solicitudId,
    UUID depositoId,
    String descripcion
) {

    public ActualizarContenedorRequest {
        if (pesoKg != null && pesoKg.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        if (volumenM3 != null && volumenM3.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El volumen no puede ser negativo");
        }
    }
}
