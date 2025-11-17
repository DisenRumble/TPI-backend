package backend.grupo73.rutas_svc.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TramoModel {

    private PuntoRutaModel origen;
    private PuntoRutaModel destino;

    private double distanciaKm;
    private double duracionMinutos;
}
