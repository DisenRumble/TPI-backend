package backend.grupo73.solicitudes_svc.domain.dto.response.rutas;

import lombok.Data;
import java.util.List;

@Data
public class AlternativaRes {
    private Long id;
    private boolean esPrincipal;
    private List<TramoRes> tramos;
    private Double distanciaTotalKm;
    private Integer duracionTotalMinutos;
}
