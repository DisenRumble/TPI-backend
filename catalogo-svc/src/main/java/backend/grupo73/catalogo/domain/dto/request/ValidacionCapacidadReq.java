package backend.grupo73.catalogo.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidacionCapacidadReq {
    private UUID camionId;
    private BigDecimal pesoTotal;
    private BigDecimal volumenTotal;
}
