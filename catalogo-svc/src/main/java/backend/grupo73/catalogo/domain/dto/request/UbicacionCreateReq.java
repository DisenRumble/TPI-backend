package backend.grupo73.catalogo.domain.dto.request;

import backend.grupo73.catalogo.domain.entity.TipoUbicacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionCreateReq {
    private String nombre;
    private String descripcion;
    private double lat;
    private double lng;
    private TipoUbicacion tipo;
}
