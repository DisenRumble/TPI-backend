package backend.grupo73.catalogo.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidacionCapacidadReq {
    private Long camionId;
    private double pesoTotal;
    private double volumenTotal;
}
