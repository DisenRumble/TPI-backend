package backend.grupo73.solicitudes_svc.domain.dto.response;

import java.util.UUID;

public record ClienteRes(
        UUID id,
        String email,
        String nombre,
        String apellido,
        String telefono,
        String direccion
) {}
