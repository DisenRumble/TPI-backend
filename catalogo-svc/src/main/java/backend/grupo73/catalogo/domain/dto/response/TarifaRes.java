package backend.grupo73.catalogo.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarifaRes {
    private Long id;
    private String name;
    private double precio;
}
