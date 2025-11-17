package backend.grupo73.catalogo.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarifaRes {
    private UUID id;
    private BigDecimal tarifaPorKm;
    private BigDecimal tarifaPorKgKm;
    private BigDecimal tarifaEstadiaPorHora;
    private boolean activa;
}
