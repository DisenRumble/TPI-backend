package backend.grupo73.solicitudes_svc.domain.dto.response.rutas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaRes {
    private Long id;
    private List<TramoRes> tramos;
    private BigDecimal costoTotal;
    private int tiempoTotal;
}
