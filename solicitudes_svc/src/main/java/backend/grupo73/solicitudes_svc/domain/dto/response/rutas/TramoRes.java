package backend.grupo73.solicitudes_svc.domain.dto.response.rutas;

import lombok.Data;

@Data
public class TramoRes {
    private PuntoRes origen;
    private PuntoRes destino;
    private Double distanciaKm;
    private Integer duracionMinutos;
}
