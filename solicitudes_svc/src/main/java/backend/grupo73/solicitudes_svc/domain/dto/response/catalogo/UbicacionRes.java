package backend.grupo73.solicitudes_svc.domain.dto.response.catalogo;

import lombok.Data;
import java.util.UUID;

@Data
public class UbicacionRes {
    private UUID id;
    private String nombre;
    private String descripcion;
    private Double lat;
    private Double lng;
    private String tipo;
}
