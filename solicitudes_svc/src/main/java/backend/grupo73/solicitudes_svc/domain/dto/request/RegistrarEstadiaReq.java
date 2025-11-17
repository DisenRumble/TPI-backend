package backend.grupo73.solicitudes_svc.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record RegistrarEstadiaReq(
        @NotNull
        UUID depositoUbicacionId,
        @NotNull
        Instant fechaHoraEntrada,
        Instant fechaHoraSalida // Puede ser null si aún no ha salido
) {}
