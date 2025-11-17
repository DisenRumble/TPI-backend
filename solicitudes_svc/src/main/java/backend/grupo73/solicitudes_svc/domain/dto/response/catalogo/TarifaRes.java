package backend.grupo73.solicitudes_svc.domain.dto.response.catalogo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TarifaRes {
    private UUID id;
    private BigDecimal tarifaPorKm;
    private BigDecimal tarifaPorKgKm; // o tarifaPorM3Km, según el diseño
    private BigDecimal tarifaEstadiaPorHora;
    // Opcional: campos para diferenciar por tipo de camión o zona.
}
