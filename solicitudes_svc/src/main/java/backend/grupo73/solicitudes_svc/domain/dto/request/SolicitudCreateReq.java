package backend.grupo73.solicitudes_svc.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record SolicitudCreateReq(
        UUID clienteId,

        @NotNull
        String identificacionUnicaContenedor,

        @NotNull
        BigDecimal peso,

        @NotNull
        BigDecimal volumen,

        @NotNull
        UUID origenUbicacionId,

        List<UUID> depositosIntermediosUbicacionIds, // Nuevo campo para depósitos intermedios

        @NotNull
        UUID destinoUbicacionId
) {}
