package backend.grupo73.catalogo.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamionCreateReq {
    private String patente;
    private double capacidadPeso;
    private double capacidadVolumen;
}
