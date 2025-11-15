package backend.grupo73.catalogo.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamionRes {
    private Long id;
    private String patente;
    private double capacidadPeso;
    private double capacidadVolumen;
}
