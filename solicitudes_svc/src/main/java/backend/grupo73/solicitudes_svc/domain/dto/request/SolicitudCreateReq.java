package backend.grupo73.solicitudes_svc.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record SolicitudCreateReq(
        UUID clienteId, // si viene null crear cliente en users-svc

        @NotBlank
        String identificacionUnicaContenedor,

        @NotNull
        BigDecimal peso,

        @NotNull
        BigDecimal volumen,

        @NotBlank
        String origen,

        @NotBlank
        String destino
) {}
