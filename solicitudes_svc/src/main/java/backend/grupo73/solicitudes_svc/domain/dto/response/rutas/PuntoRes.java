package backend.grupo73.solicitudes_svc.domain.dto.response.rutas;

import lombok.Data;

@Data
public class PuntoRes {
    private Double lat;
    private Double lng;
    private String tipo;
    private String descripcion;
}
