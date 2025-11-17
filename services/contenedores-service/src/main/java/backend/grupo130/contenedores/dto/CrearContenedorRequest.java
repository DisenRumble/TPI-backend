package backend.grupo130.contenedores.dto;

import backend.grupo130.contenedores.entity.EstadoContenedor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CrearContenedorRequest(
    @NotBlank String identificador,
    @NotNull @DecimalMin("0.0") BigDecimal pesoKg,
    @NotNull @DecimalMin("0.0") BigDecimal volumenM3,
    @NotNull UUID clienteId,
    UUID solicitudId,
    UUID depositoId,
    EstadoContenedor estado,
    String descripcion
) { }
