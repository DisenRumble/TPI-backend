package backend.grupo73.rutas_svc.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PuntoRutaReq(

        @NotNull
        Double lat,

        @NotNull
        Double lng,

        @NotBlank
        String tipo,

        String descripcion
) {}
