package backend.grupo73.rutas_svc.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CalcularRutaReq(

        @NotEmpty
        @Size(min = 2, message = "Se necesitan al menos 2 puntos para calcular la ruta")
        List<@Valid PuntoRutaReq> puntos
) {}
