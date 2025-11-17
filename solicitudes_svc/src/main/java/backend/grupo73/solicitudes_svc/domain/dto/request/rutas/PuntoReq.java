package backend.grupo73.solicitudes_svc.domain.dto.request.rutas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuntoReq {
    private String tipo;
    private String descripcion;
    private Double lat;
    private Double lng;
}
