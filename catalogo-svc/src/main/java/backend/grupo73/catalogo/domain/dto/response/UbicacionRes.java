package backend.grupo73.catalogo.domain.dto.response;

import backend.grupo73.catalogo.domain.entity.TipoUbicacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionRes {
    private UUID id;
    private String nombre;
    private String descripcion;
    private double lat;
    private double lng;
    private TipoUbicacion tipo;
    private boolean activo;
}
