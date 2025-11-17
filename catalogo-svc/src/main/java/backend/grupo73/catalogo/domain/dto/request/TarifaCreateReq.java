package backend.grupo73.catalogo.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarifaCreateReq {
    private BigDecimal tarifaPorKm;
    private BigDecimal tarifaPorKgKm;
    private BigDecimal tarifaEstadiaPorHora;
    private boolean activa;
}
