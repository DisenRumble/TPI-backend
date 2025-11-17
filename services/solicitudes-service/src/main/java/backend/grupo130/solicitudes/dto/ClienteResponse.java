package backend.grupo130.solicitudes.dto;

import java.util.UUID;

public record ClienteResponse(
    UUID id,
    String dni,
    String nombre,
    String apellido,
    String telefono,
    String email,
    UUID usuarioId
) { }
