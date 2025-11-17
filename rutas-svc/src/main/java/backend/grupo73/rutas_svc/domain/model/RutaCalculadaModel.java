package backend.grupo73.rutas_svc.domain.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RutaCalculadaModel {

    private List<TramoModel> tramos;

    private double distanciaTotalKm;
    private double duracionTotalMinutos;
}
