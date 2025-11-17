package backend.grupo73.rutas_svc.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PuntoRutaModel {

    private Double lat;
    private Double lng;
    private String tipo;
    private String descripcion;
}
