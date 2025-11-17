package backend.grupo73.solicitudes_svc.domain.dto.response.rutas;

import lombok.Data;
import java.util.List;

@Data
public class CalcularRutaRes {
    private List<AlternativaRes> alternativas;
}
