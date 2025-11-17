package backend.grupo130.camiones.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransportistaResponse(
    UUID id,
    String nombreCompleto,
    String telefono,
    String email,
    UUID usuarioId,
    String licencia,
    BigDecimal salario,
    boolean activo
) { }
