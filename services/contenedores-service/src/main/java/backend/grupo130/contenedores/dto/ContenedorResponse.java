package backend.grupo130.contenedores.dto;

import backend.grupo130.contenedores.entity.EstadoContenedor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ContenedorResponse(
    UUID id,
    String identificador,
    BigDecimal pesoKg,
    BigDecimal volumenM3,
    EstadoContenedor estado,
    UUID clienteId,
    UUID solicitudId,
    UUID depositoId,
    String descripcion,
    LocalDateTime fechaAlta,
    LocalDateTime fechaActualizacion
) { }
