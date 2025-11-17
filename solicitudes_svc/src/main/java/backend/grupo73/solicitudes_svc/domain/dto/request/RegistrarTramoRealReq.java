package backend.grupo73.solicitudes_svc.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record RegistrarTramoRealReq(
        @NotNull
        Double distanciaRealKm,
        @NotNull
        Integer tiempoRealMin
) {}
