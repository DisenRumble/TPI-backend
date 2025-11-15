package backend.grupo73.solicitudes_svc.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AsignarRutaReq(
        @NotNull
        UUID rutaId
) {}
