package backend.grupo73.solicitudes_svc.domain.dto.request.rutas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalcularRutaReq {
    private List<PuntoReq> puntos;
}
