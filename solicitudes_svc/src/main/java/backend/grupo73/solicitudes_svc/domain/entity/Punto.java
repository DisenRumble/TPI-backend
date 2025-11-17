package backend.grupo73.solicitudes_svc.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Punto {
    private Double lat;
    private Double lng;
    private String tipo;
    private String descripcion;
}
